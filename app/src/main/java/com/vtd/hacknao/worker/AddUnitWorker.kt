package com.vtd.hacknao.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vtd.hacknao.data.local.db.AppDatabase
import com.vtd.hacknao.database.DatabaseAccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddUnitWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val listData = DatabaseAccess.getInstance(applicationContext).getVocabularyList()
            AppDatabase.getInstance(applicationContext).vocabularyDao()
                .insertVocabularyList(listData)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}