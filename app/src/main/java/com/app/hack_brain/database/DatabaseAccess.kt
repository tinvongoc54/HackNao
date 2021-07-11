package com.app.hack_brain.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.hack_brain.data.local.entity.TargetEntity
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.extension.convertTimestampToDate
import timber.log.Timber
import java.util.*

class DatabaseAccess(context: Context) {
    private var openHelper: SQLiteOpenHelper = DatabaseOpenHelper(context)
    private var database: SQLiteDatabase? = null

    init {
        this.openHelper = DatabaseOpenHelper(context)
    }

    fun open() {
        database = openHelper.writableDatabase
    }

    /**
     * Close the databases connection.
     */
    fun close() {
        database?.close()
    }

    fun getDatabase(): SQLiteDatabase? {
        return database
    }

    fun getUnitList(): List<UnitEntity> {
        database = openHelper.readableDatabase
        val list = mutableListOf<UnitEntity>()
        val cursor: Cursor = database!!.rawQuery(
            "SELECT * FROM 'unit_tracking' ORDER BY unit",
            null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val unit = UnitEntity(
                id = cursor.getInt(0),
                unit = cursor.getInt(1),
                progressEngVie = cursor.getInt(2),
                progressVieEng = cursor.getInt(3),
                progressSound = cursor.getInt(4)
            )
            list.add(unit)
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun getVocabularyList(): List<VocabularyEntity> {
        database = openHelper.readableDatabase
        val list = mutableListOf<VocabularyEntity>()
        val cursor: Cursor = database!!.rawQuery(
            "SELECT * FROM 'dict_en_vi' ORDER BY id",
            null
        )
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val voc = VocabularyEntity(
                id = cursor.getInt(0),
                word = cursor.getString(1),
                phonetic = cursor.getString(2),
                meanings = cursor.getString(3),
                note = cursor.getString(4),
                isFavourite = cursor.getInt(5),
                isLearnedVieEng = cursor.getInt(6),
                isLearnedEngVie = cursor.getInt(7),
                isLearnedSound = cursor.getInt(8),
                shortMean = cursor.getString(9)
            )
            list.add(voc)
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    fun getTargetList(): List<TargetEntity> {
        database = openHelper.readableDatabase
        val list = mutableListOf<TargetEntity>()
        val cursor: Cursor = database!!.rawQuery(
            "SELECT * FROM 'muctieu' ORDER BY id",
            null
        )
        cursor.moveToFirst()
        val today = Calendar.getInstance()
        while (!cursor.isAfterLast) {
            val voc = TargetEntity(
                id = cursor.getInt(0),
                unit = cursor.getInt(1),
                date = convertTimestampToDate(today.timeInMillis),
                status = 1
            )
            Timber.i("" + cursor.getInt(0))
            today.add(Calendar.DATE, 1)
            list.add(voc)
            cursor.moveToNext()
        }
        cursor.close()
        return list
    }

    companion object {
        private var instance: DatabaseAccess? = null

        fun getInstance(context: Context?): DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context!!)
            }
            return instance!!
        }
    }
}