package com.app.hack_brain.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.app.hack_brain.R
import com.app.hack_brain.common.Constant
import com.app.hack_brain.ui.home.HomeActivity
import com.app.hack_brain.worker.RemindTargetWorker
import com.app.hack_brain.worker.RemindVocabularyWorker
import java.util.*
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val scheduleOpenApp = intent.getStringExtra(Constant.EXTRA_TIMER_SCHEDULE_OPEN_APP) ?: ""

        val scheduleRemind = intent.getStringExtra(Constant.EXTRA_TIMER_SCHEDULE_REMIND) ?: ""
        val unit = intent.getIntExtra(Constant.EXTRA_UNIT, 0)
        val waitingTime = intent.getIntExtra(Constant.EXTRA_TIMER_SCHEDULE_WAITING_TIME, 0)

        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()

        when (intent.action) {
            Constant.ACTION_OPEN_APP -> {
                if (scheduleOpenApp.contains(dayOfWeek)) {
                    buildNotificationOpenApp(context)
                }
            }
            Constant.ACTION_REMIND_VOCABULARY -> {
                if (scheduleRemind.contains(dayOfWeek)) {
                    val data = Data.Builder()
                    data.putInt(Constant.EXTRA_UNIT, unit)
                    data.putInt(Constant.EXTRA_TIMER_SCHEDULE_WAITING_TIME, waitingTime)
                    val work = OneTimeWorkRequestBuilder<RemindVocabularyWorker>().setInputData(data.build()).build()
                    WorkManager.getInstance(context).enqueue(work)
                }
            }
            Constant.ACTION_TARGET -> {
                val work = OneTimeWorkRequestBuilder<RemindTargetWorker>().build()
                WorkManager.getInstance(context).enqueue(work)
            }
        }
    }

    private fun buildNotificationOpenApp(context: Context) {
        val notifyIntent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            context, Constant.REQUEST_CODE_OPEN_APP, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setContentIntent(notifyPendingIntent)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("3K Words")
            .setContentText("Đến giờ học bài rồi!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notify = NotificationManagerCompat.from(context)
        notify.notify(Random.nextInt(), builder.build())
    }
}