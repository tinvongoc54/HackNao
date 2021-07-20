package com.app.hack_brain.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.hack_brain.R
import com.app.hack_brain.common.Constant
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constant.EXTRA_EXACT_TIME, 0L)

        when (intent.action) {
            Constant.ACTION_SET_EXACT_TIME -> {
                buildNotification(context, "exact", "exact")
            }
            Constant.ACTION_SET_REPEAT_TIME -> {
                setRepeatAlarm(AlarmService(context), timeInMillis)
                buildNotification(context, "repeat", "repeat")
            }
        }
    }

    private fun setRepeatAlarm(alarmService: AlarmService, timeInMillis: Long) {
        val cal = Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
        }
        alarmService.setRepeat(cal.timeInMillis)
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        val builder = NotificationCompat.Builder(context, "notifycc")
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val noti = NotificationManagerCompat.from(context)
        noti.notify(200, builder.build())
    }
}