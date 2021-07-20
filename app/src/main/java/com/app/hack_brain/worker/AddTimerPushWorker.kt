package com.app.hack_brain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.hack_brain.common.Constant
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.data.local.entity.TimerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AddTimerPushWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 19)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            val timer1 = TimerEntity(
                id = 1,
                time = calendar.timeInMillis,
                repeat = "1,2,3,4,5,6,7",
                vocabulary = null,
                type = Constant.TYPE_OPEN_APP,
                waitingTime = null,
                isTurnOn = false,
                repeatNumber = null
            )
            val timer2 = TimerEntity(
                id = 2,
                time = calendar.timeInMillis,
                repeat = "1,2,3,4,5,6,7",
                vocabulary = 1,
                type = Constant.TYPE_REMIND_VOCABULARY,
                waitingTime = 15,
                isTurnOn = false,
                repeatNumber = 1
            )
            val timerList = mutableListOf(timer1, timer2)
            AppDatabase.getInstance(applicationContext).timerDao().insertTimer(timerList)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}