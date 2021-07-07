package com.app.hack_brain.ui.timer.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.hack_brain.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val builder = NotificationCompat.Builder(context, "notifycc")
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Alarm")
            .setContentText("Subcribe")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val noti = NotificationManagerCompat.from(context)
        noti.notify(200, builder.build())
    }
}