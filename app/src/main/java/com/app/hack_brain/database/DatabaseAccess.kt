package com.app.hack_brain.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.hack_brain.common.Constant
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.data.local.entity.VocabularyEntity
import timber.log.Timber

class DatabaseAccess(context: Context) {
    private var openHelper: SQLiteOpenHelper = DatabaseOpenHelper(context)
    private var database: SQLiteDatabase? = null

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

//    fun getWordUnit(amount: Int, unit: Int): List<VocabularyEntity> {
//        database = openHelper.readableDatabase
//        val list: MutableList<VocabularyEntity> = ArrayList()
//        val cursor: Cursor = database!!.rawQuery("SELECT * FROM 'dict_en_vi' ORDER BY LENGTH(word) LIMIT $amount OFFSET ${amount*unit}", null)
//        cursor.moveToFirst()
//        while (!cursor.isAfterLast) {
//            val vocabulary = VocabularyEntity(
//                id = cursor.getInt(0),
//                word = cursor.getString(1),
//                phonetic = cursor.getString(2),
//                meanings = cursor.getString(3),
//                note = cursor.getString(4),
//                isFavourite = cursor.getInt(5),
//                isLearnedEngVie = cursor.getInt(6),
//                isLearnedVieEng = cursor.getInt(7),
//                isLearnedSound = cursor.getInt(8),
//                shortMean = cursor.getString(9)
//            )
//            list.add(vocabulary)
//            cursor.moveToNext()
//        }
//        cursor.close()
//        return list
//    }

//    fun getUnitList(): List<UnitEntity> {
//        database = openHelper.readableDatabase
//        val list = mutableListOf<UnitEntity>()
//        val cursor: Cursor = database!!.rawQuery("SELECT * FROM 'unit_tracking' ORDER BY unit", null)
//        cursor.moveToFirst()
//        while (!cursor.isAfterLast) {
//            val unit = UnitEntity(
//                id = cursor.getInt(0),
//                unit = cursor.getInt(1),
//                progressEngVie = cursor.getFloat(2),
//                progressVieEng = cursor.getFloat(3),
//                progressSound = cursor.getFloat(4)
//            )
//            list.add(unit)
//        }
//        cursor.close()
//        return list
//    }

    fun createUnitList() {
//        database = openHelper.writableDatabase
//        val totalUnit = Constant.TOTAL_ROW_DB / Constant.AMOUNT_VOC_AN_UNIT
//        var values = ""
//        for (i in 1..totalUnit) {
//            values += "($i, $i),"
//        }
//        Timber.i("value: ${values.dropLast(1)}")
//        val cursor = database!!.rawQuery("INSERT INTO 'unit_tracking'(id, unit) VALUES ${values.dropLast(1)}", null)
//        cursor.close()
    }

    companion object {
        private var instance: DatabaseAccess? = null

        fun getInstance(context: Context?): DatabaseAccess? {
            if (instance == null) {
                instance = DatabaseAccess(context!!)
            }
            return instance
        }
    }
}