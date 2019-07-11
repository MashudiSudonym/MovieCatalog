package c.dicodingmade.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import c.dicodingmade.R
import c.dicodingmade.util.notificationSetup
import java.util.*

class ReleaseNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val ID_REPEATING = 10
        const val CHANNEL_ID = 1
        const val NOTIFICATION_CHANNEL = "Release Reminder"
        const val TITLE = "TITLE"
        const val CONTENT = "CONTENT"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = "${context?.resources?.getString(R.string.release_Today)} ${intent?.getStringExtra(TITLE)}"
        val content = intent?.getStringExtra(CONTENT)
        notificationSetup(context, title, content, CHANNEL_ID, NOTIFICATION_CHANNEL)
    }

    fun setAlarmRepeat(context: Context?, upcomingTitle: String?, upcomingOverview: String?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dailyDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val pendingIntent = pendingIntentService(context, upcomingTitle, upcomingOverview)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            dailyDate.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun pendingIntentService(
        context: Context?,
        upcomingTitle: String?,
        upcomingOverview: String?
    ): PendingIntent? {
        val intent = Intent(context, DailyNotificationReceiver::class.java).apply {
            putExtra(TITLE, upcomingTitle)
            putExtra(CONTENT, upcomingOverview)
        }
        return PendingIntent.getBroadcast(
            context,
            ID_REPEATING, intent, 0
        )
    }

    fun cancelAlarmRepeat(context: Context?, upcomingTitle: String?, upcomingOverview: String?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = pendingIntentService(context, upcomingTitle, upcomingOverview)
        alarmManager.cancel(pendingIntent)
    }
}