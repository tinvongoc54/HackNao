package com.app.hack_brain.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.hack_brain.R
import com.app.hack_brain.app.App
import com.app.hack_brain.common.Constant
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.extension.nullToBlank
import com.app.hack_brain.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

class RemindVocabularyWorker(val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val unit = inputData.getInt(Constant.EXTRA_UNIT, 0)
        val waitingTime = inputData.getInt(Constant.EXTRA_TIMER_SCHEDULE_WAITING_TIME, 15)
        Timber.i("unit: $unit, waiting time: $waitingTime")
        val vocabularyDao = AppDatabase.getInstance(context).vocabularyDao()

        val wordList =
            if (unit == 0) vocabularyDao.getFavouriteVocList() else vocabularyDao.getVocabularyOfUnit(
                Constant.AMOUNT_VOC_AN_UNIT,
                unit
            )

        kotlin.run {
            wordList.forEach {
                if (App.isRunning()) {
                    return@run
                } else {
                    buildNotification(context, it.word.nullToBlank(), it.shortMean.nullToBlank())
                    delay(waitingTime.toLong() * 1000)
                }
            }
        }

        Result.success()
    }

    private fun buildNotification(
        context: Context,
        title: String,
        message: String
    ) {
        val notifyIntent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            context, Constant.REQUEST_CODE_REMIND_VOCABULARY, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setContentIntent(notifyPendingIntent)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notify = NotificationManagerCompat.from(context)
        notify.notify(Random.nextInt(), builder.build())
    }
}