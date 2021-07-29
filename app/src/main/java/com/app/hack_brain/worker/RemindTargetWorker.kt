package com.app.hack_brain.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.hack_brain.R
import com.app.hack_brain.common.Constant
import com.app.hack_brain.data.local.db.AppDatabase
import com.app.hack_brain.extension.convertTimestampToDate
import com.app.hack_brain.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

class RemindTargetWorker(val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val date = convertTimestampToDate(Calendar.getInstance().timeInMillis)
        val targetList = AppDatabase.getInstance(context).targetDao().getTargetListOfDay(date)
        targetList.forEach {
            buildNotification(context, "Mục tiêu hôm nay của bạn là Unit ${it.unit}")
        }
        Result.success()
    }

    private fun buildNotification(context: Context, message: String) {
        val notifyIntent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            context, Constant.REQUEST_CODE_REMIND_TARGET, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setContentIntent(notifyPendingIntent)
            .setSmallIcon(R.drawable.img_logo)
            .setContentTitle("3K Words")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notify = NotificationManagerCompat.from(context)
        notify.notify(Random.nextInt(), builder.build())
    }
}