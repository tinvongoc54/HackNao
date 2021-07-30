package com.vtd.hacknao.ui.timer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vtd.hacknao.R
import com.vtd.hacknao.common.Constant

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val builder = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setSmallIcon(R.drawable.img_logo)
            .setContentTitle("Alarm")
            .setContentText("Subcribe")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val noti = NotificationManagerCompat.from(context)
        noti.notify(200, builder.build())
    }
}