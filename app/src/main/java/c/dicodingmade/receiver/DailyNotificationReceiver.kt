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
        const val ID_REPEATING = 10
        const val CHANNEL_ID = 122
        const val NOTIFICATION_CHANNEL = "Daily Reminder"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = context?.resources?.getString(R.string.app_name)
        val content = context?.resources?.getString(R.string.notif_daily_body)
        notificationSetup(context, title, content, CHANNEL_ID, NOTIFICATION_CHANNEL)
    }

    fun setAlarmRepeat(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dailyDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val pendingIntent = pendingIntentService(context)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            dailyDate.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun pendingIntentService(context: Context?): PendingIntent? {
        val intent = Intent(context, DailyNotificationReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            ID_REPEATING, intent, 0
        )
    }

    fun cancelAlarmRepeat(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = pendingIntentService(context)
        alarmManager.cancel(pendingIntent)
    }
}