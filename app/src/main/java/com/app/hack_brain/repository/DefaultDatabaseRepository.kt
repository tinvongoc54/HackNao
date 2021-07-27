package com.app.hack_brain.repository

import com.app.hack_brain.common.Constant
import com.app.hack_brain.common.base.BaseRepository
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.data.local.entity.TargetEntity
import com.app.hack_brain.data.local.entity.TimerEntity
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.extension.toInt
import com.app.hack_brain.utils.DataResult

class DefaultDatabaseRepository(private val appDatabase: AppDatabase) : DatabaseRepository, BaseRepository() {
    override suspend fun getVocabularyOfUnit(unit: Int): DataResult<List<VocabularyEntity>> {
        return withResultContext {
            appDatabase.vocabularyDao().getVocabularyOfUnit(Constant.AMOUNT_VOC_AN_UNIT, unit)
        }
    }

    override suspend fun insertVocabulary(vocabulary: VocabularyEntity): DataResult<Any> {
        return withResultContext {
            appDatabase.vocabularyDao().insertVocabulary(vocabulary)
        }
    }

    override suspend fun getRandomVocabulary(): DataResult<List<VocabularyEntity>> {
        return withResultContext {
            appDatabase.vocabularyDao().getRandomVocabulary()
        }
    }

    override suspend fun setFavouriteVoc(id: Int, isFavourite: Boolean): DataResult<Unit> {
        return withResultContext {
            appDatabase.vocabularyDao().setFavouriteVoc(id, isFavourite.toInt())
        }
    }

    override suspend fun getFavouriteVocList(): DataResult<List<VocabularyEntity>> {
        return withResultContext {
            appDatabase.vocabularyDao().getFavouriteVocList()
        }
    }

    override suspend fun getUnitList(): DataResult<List<UnitEntity>> {
        return withResultContext {
            appDatabase.unitDao().getUnitList()
        }
    }

    override suspend fun getUnit(unit: Int): DataResult<UnitEntity> {
        return withResultContext {
            appDatabase.unitDao().getUnit(unit)
        }
    }

    override suspend fun updateEngVieProgress(unit: Int, progress: Int): DataResult<Unit> {
        return withResultContext {
            appDatabase.unitDao().updateEngVieProgress(unit, progress)
        }
    }

    override suspend fun updateVieEngProgress(unit: Int, progress: Int): DataResult<Unit> {
        return withResultContext {
            appDatabase.unitDao().updateVieEngProgress(unit, progress)
        }
    }

    override suspend fun updateSoundProgress(unit: Int, progress: Int): DataResult<Unit> {
        return withResultContext {
            appDatabase.unitDao().updateSoundProgress(unit, progress)
        }
    }

    override suspend fun addTarget(target: List<TargetEntity>): DataResult<Any> {
        return withResultContext {
            appDatabase.targetDao().insertTargetList(target)
        }
    }

    override suspend fun getTargetList(): DataResult<List<TargetEntity>> {
        return withResultContext {
            appDatabase.targetDao().getAll()
        }
    }

    override suspend fun getTarget(date: String): DataResult<TargetEntity> {
        return withResultContext {
            appDatabase.targetDao().getTargetEntity(date)
        }
    }

    override suspend fun updateTarget(target: TargetEntity): DataResult<Unit> {
        return withResultContext {
            appDatabase.targetDao().updateTarget(target)
        }
    }

    override suspend fun updateTargetByUnit(unit: Int, status: Int): DataResult<Unit> {
        return withResultContext {
            appDatabase.targetDao().updateTargetByUnit(unit, status)
        }
    }

    override suspend fun insertTimer(timer: TimerEntity): DataResult<Any> {
        return withResultContext { 
            appDatabase.timerDao().insertTimer(timer)
        }
    }

    override suspend fun updateTimer(timer: TimerEntity): DataResult<Any> {
        return withResultContext {
            appDatabase.timerDao().updateTimer(timer)
        }
    }

    override suspend fun deleteTimer(timer: TimerEntity): DataResult<Any> {
        return withResultContext { 
            appDatabase.timerDao().deleteTimer(timer)
        }
    }

    override suspend fun getTimerList(): DataResult<List<TimerEntity>> {
        return withResultContext {
            appDatabase.timerDao().getAll()
        }
    }
}

interface DatabaseRepository {
    suspend fun getVocabularyOfUnit(unit: Int): DataResult<List<VocabularyEntity>>
    suspend fun insertVocabulary(vocabulary: VocabularyEntity): DataResult<Any>
    suspend fun getRandomVocabulary(): DataResult<List<VocabularyEntity>>
    suspend fun setFavouriteVoc(id: Int, isFavourite: Boolean): DataResult<Unit>
    suspend fun getFavouriteVocList(): DataResult<List<VocabularyEntity>>

    suspend fun getUnitList(): DataResult<List<UnitEntity>>
    suspend fun getUnit(unit: Int): DataResult<UnitEntity>
    suspend fun updateEngVieProgress(unit: Int, progress: Int): DataResult<Unit>
    suspend fun updateVieEngProgress(unit: Int, progress: Int): DataResult<Unit>
    suspend fun updateSoundProgress(unit: Int, progress: Int): DataResult<Unit>

    suspend fun addTarget(target: List<TargetEntity>): DataResult<Any>
    suspend fun getTargetList(): DataResult<List<TargetEntity>>
    suspend fun getTarget(date: String): DataResult<TargetEntity>
    suspend fun updateTarget(target: TargetEntity): DataResult<Unit>
    suspend fun updateTargetByUnit(unit: Int, status: Int): DataResult<Unit>

    suspend fun insertTimer(timer: TimerEntity): DataResult<Any>
    suspend fun updateTimer(timer: TimerEntity): DataResult<Any>
    suspend fun deleteTimer(timer: TimerEntity): DataResult<Any>
    suspend fun getTimerList(): DataResult<List<TimerEntity>>
}