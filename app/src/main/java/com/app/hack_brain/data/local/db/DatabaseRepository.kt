package com.app.hack_brain.data.local.db

import com.app.hack_brain.common.base.BaseRepository
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.utils.DataResult
import timber.log.Timber

class DefaultDatabaseRepository(private val appDatabase: AppDatabase) : DatabaseRepository, BaseRepository() {
    override suspend fun getVocabularyOfUnit(unit: Int): DataResult<List<VocabularyEntity>> {
        return withResultContext {
            appDatabase.vocabularyDao().getAll(20, unit)
        }
    }

    override suspend fun insertVocabulary(vocabulary: VocabularyEntity): DataResult<Any> {
        return withResultContext {
            appDatabase.vocabularyDao().insertVocabulary(vocabulary)
        }
    }

    override suspend fun getUnitList(): DataResult<List<UnitEntity>> {
        return withResultContext {
            appDatabase.unitDao().getUnitList()
        }
    }

    override suspend fun insertUnit(unit: UnitEntity): DataResult<Unit> {
        return withResultContext {
            appDatabase.unitDao().insertUnit(unit)
        }
    }
}

interface DatabaseRepository {
    suspend fun getVocabularyOfUnit(unit: Int): DataResult<List<VocabularyEntity>>
    suspend fun insertVocabulary(vocabulary: VocabularyEntity): DataResult<Any>
    suspend fun getUnitList(): DataResult<List<UnitEntity>>
    suspend fun insertUnit(unit: UnitEntity): DataResult<Unit>
}