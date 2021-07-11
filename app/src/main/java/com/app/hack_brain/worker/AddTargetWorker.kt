package com.app.hack_brain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.database.DatabaseAccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddTargetWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val listData = DatabaseAccess.getInstance(applicationContext).getTargetList()
            AppDatabase.getInstance(applicationContext).targetDao()
                .insertTargetList(listData)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}