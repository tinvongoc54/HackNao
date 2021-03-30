package com.app.hack_brain.ui.check

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.app.hack_brain.model.uimodel.Word
import timber.log.Timber
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class DatabaseHelper(private val myContext: Context) :
    SQLiteOpenHelper(myContext, DB_NAME, null, 10) {

    private var DB_PATH: String? = null
    private var myDataBase: SQLiteDatabase? = null

    init {
        DB_PATH = myContext.applicationInfo.dataDir + "/databases/"
    }

    @Synchronized
    override fun close() {
        if (myDataBase != null) myDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) try {
            copyDataBase()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if (!dbExist) {
            this.readableDatabase
            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
        }
        checkDB?.close()
        return checkDB != null
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        val myInput = myContext.assets.open(DB_NAME)
        val outFileName = DB_PATH + DB_NAME
        val myOutput: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(10)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase() {
        val myPath = DB_PATH + DB_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    fun getAllWord(unit: Int, numberWord: Int = 20): List<Word> {
        val result = mutableListOf<Word>()
        val sqLiteDatabase = this.readableDatabase
        val sql = "SELECT * FROM dict_en_vi LIMIT $numberWord OFFSET $unit * $numberWord"
        val cursor = sqLiteDatabase.rawQuery(sql, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            result.add(
                Word(
                    id = cursor.getInt(0),
                    word = cursor.getString(1),
                    phonetic = cursor.getString(2),
                    meanings = cursor.getString(3)
                )
            )
            cursor.moveToNext()
        }
        cursor.close()
        return result
    }

    companion object {
        private const val DB_NAME = "myDatabase"
    }
}