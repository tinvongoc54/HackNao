package com.app.hack_brain.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.hack_brain.data.local.dao.TargetDao
import com.app.hack_brain.data.local.dao.TimerDao
import com.app.hack_brain.data.local.dao.VocabularyDao
import com.app.hack_brain.data.local.entity.TargetEntity
import com.app.hack_brain.data.local.entity.TimerEntity
import com.app.hack_brain.data.local.entity.VocabularyEntity
import timber.log.Timber

/**
 * https://developer.android.com/training/data-storage/room
 */
@Database(
    entities = [VocabularyEntity::class, TargetEntity::class, TimerEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao
    abstract fun timerDao(): TimerDao
    abstract fun targetDao(): TargetDao

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
            Timber.i("buildRoomDB")
            return Room.databaseBuilder(context, AppDatabase::class.java, "My Database")
                .createFromAsset("myDatabase.db")
                .build()
        }
    }
}
