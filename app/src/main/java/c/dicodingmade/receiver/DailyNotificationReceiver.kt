package c.dicodingmade.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import c.dicodingmade.R
import c.dicodingmade.util.notificationSetup
import java.util.*

class DailyNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val DAILY_REPEATING = 100
        const val CHANNEL_ID = 122
        const val DAILY_NOTIFICATION_CHANNEL = "Daily Reminder"
        const val EXTRA_TITLE = "title"
        const val EXTRA_CONTENT = "content"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(EXTRA_TITLE)
        val content = intent?.getStringExtra(EXTRA_CONTENT)
        notificationSetup(context, title, content, CHANNEL_ID, DAILY_NOTIFICATION_CHANNEL)
    }

    fun setDailyAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmSchedule = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val currentDate = Calendar.getInstance()
        val title = context.resources.getString(R.string.app_name)
        val content = context.resources.getString(R.string.notif_daily_body)
        val pendingIntent = pendingIntentService(context, title, content)

        if (alarmSchedule.before(currentDate)) alarmSchedule.add(Calendar.HOUR_OF_DAY, 24)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmSchedule.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun pendingIntentService(context: Context?, title: String?, content: String?): PendingIntent? {
        val intent = Intent(context, DailyNotificationReceiver::class.java).apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_CONTENT, content)
        }
        return PendingIntent.getBroadcast(
            context,
            DAILY_REPEATING,
            intent,
            0
        )
    }

    fun cancelAlarmDaily(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            DAILY_REPEATING,
            intent,
            0
        )
        alarmManager.cancel(pendingIntent)
    }
}