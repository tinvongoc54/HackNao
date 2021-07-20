package com.app.hack_brain.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.app.hack_brain.data.local.dao.TargetDao
import com.app.hack_brain.data.local.dao.TimerDao
import com.app.hack_brain.data.local.dao.UnitDao
import com.app.hack_brain.data.local.dao.VocabularyDao
import com.app.hack_brain.data.local.entity.TargetEntity
import com.app.hack_brain.data.local.entity.TimerEntity
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.database.DatabaseAccess
import com.app.hack_brain.database.ioThread
import com.app.hack_brain.worker.AddTargetWorker
import com.app.hack_brain.worker.AddTimerPushWorker
import com.app.hack_brain.worker.AddUnitWorker
import com.app.hack_brain.worker.AddVocabularyWorker

/**
 * https://developer.android.com/training/data-storage/room
 */
@Database(
    entities = [VocabularyEntity::class, TargetEntity::class, TimerEntity::class, UnitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao
    abstract fun timerDao(): TimerDao
    abstract fun targetDao(): TargetDao
    abstract fun unitDao(): UnitDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "oxfordRoom.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val dataVocabulary = OneTimeWorkRequestBuilder<AddVocabularyWorker>().addTag("vocabulary").build()
                        val dataUnit = OneTimeWorkRequestBuilder<AddUnitWorker>().addTag("unit").build()
                        val dataTarget = OneTimeWorkRequestBuilder<AddTargetWorker>().addTag("target").build()
                        val dataTimer = OneTimeWorkRequestBuilder<AddTimerPushWorker>().addTag("timer").build()
                        WorkManager.getInstance(context).enqueue(listOf(dataVocabulary, dataTarget, dataUnit, dataTimer))
//                        ioThread {
//                            val listUnit = DatabaseAccess.getInstance(context).getUnitList()
//                            getInstance(context).unitDao().insertUnitList(listUnit)
//                        }
//                        ioThread {
//                            val listVoc = DatabaseAccess.getInstance(context).getVocabularyList()
//                            getInstance(context).vocabularyDao().insertVocabularyList(listVoc)
//                        }
//                        ioThread {
//                            val listTarget = DatabaseAccess.getInstance(context).getTargetList()
//                            getInstance(context).targetDao().insertTargetList(listTarget)
//                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
