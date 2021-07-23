package com.app.hack_brain.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.app.hack_brain.common.Constant
import com.app.hack_brain.data.local.entity.TimerEntity
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setRepeatOpenApp(timeInMillis: Long, scheduleRepeat: String) {
        setAlarm(timeInMillis, getPendingIntentOpenApp(getIntent().apply {
            action = Constant.ACTION_OPEN_APP
            putExtra(Constant.EXTRA_TIMER_SCHEDULE_OPEN_APP, scheduleRepeat)
        }))
    }

    fun setRemindTarget() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        setAlarm(calendar.timeInMillis, getPendingIntentRemindTarget(getIntent().apply {
            action = Constant.ACTION_TARGET
        }))
    }

    fun setRepeatRemindVoc(timer: TimerEntity) {
        setAlarmRemindVoc(timer.time, getPendingIntentRemindVoc(getIntent().apply {
            action = Constant.ACTION_REMIND_VOCABULARY
            putExtra(Constant.EXTRA_TIMER_SCHEDULE_REMIND, timer.repeat)
            putExtra(Constant.EXTRA_TIMER_SCHEDULE_WAITING_TIME, timer.waitingTime ?: 15)
            putExtra(Constant.EXTRA_UNIT, timer.vocabulary ?: 0)
        }))
    }

    fun cancelAlarmOpenApp() {
        alarmManager?.cancel(getPendingIntentOpenApp(getIntent()))
    }

    fun cancelAlarmRemindVoc() {
        alarmManager?.cancel(getPendingIntentRemindVoc(getIntent()))
    }

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            TimeUnit.DAYS.toMillis(1),
            pendingIntent
        )
    }

    private fun setAlarmRemindVoc(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            TimeUnit.DAYS.toMillis(1),
            pendingIntent
        )
    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntentOpenApp(intent: Intent) =
        PendingIntent.getBroadcast(context, Constant.REQUEST_CODE_OPEN_APP, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    private fun getPendingIntentRemindVoc(intent: Intent) =
        PendingIntent.getBroadcast(context, Constant.REQUEST_CODE_REMIND_VOCABULARY, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    private fun getPendingIntentRemindTarget(intent: Intent) =
        PendingIntent.getBroadcast(context, Constant.REQUEST_CODE_REMIND_TARGET, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}