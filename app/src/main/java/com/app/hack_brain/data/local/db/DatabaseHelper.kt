package com.app.hack_brain.data.local.db

import com.app.hack_brain.common.base.BaseRepository
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.model.uimodel.Word
import com.app.hack_brain.utils.DataResult
import timber.log.Timber

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper, BaseRepository() {
    override suspend fun getAllVocabulary(): DataResult<List<VocabularyEntity>> {
        return withResultContext {
            Timber.i("get all")
            appDatabase.vocabularyDao().getAll()
        }
    }

    override suspend fun insertVocabulary(vocabulary: VocabularyEntity): DataResult<Any> {
        return withResultContext {
            appDatabase.vocabularyDao().insertVocabulary(vocabulary)
        }
    }
}

interface DatabaseHelper {
    suspend fun getAllVocabulary(): DataResult<List<VocabularyEntity>>
    suspend fun insertVocabulary(vocabulary: VocabularyEntity): DataResult<Any>
}