package c.dicodingmade.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import c.dicodingmade.R
import c.dicodingmade.database.contentMovieUpcoming.ContentUpcomingByDateEntity
import c.dicodingmade.util.notificationSetup
import java.util.*

class ReleaseNotificationReceiver : BroadcastReceiver() {
    companion object {
        var RELEASE_REPEATING = 110
        const val CHANNEL_ID = 123
        const val RELEASE_NOTIFICATION_CHANNEL = "Release Reminder"
        const val EXTRA_TITLE = "title"
        const val EXTRA_CONTENT = "content"
    }

    private var delayNotification = 0
    private var notificationTimeTrigger = 0L
    private var pendingIntent: PendingIntent? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(EXTRA_TITLE)
        val content = intent?.getStringExtra(EXTRA_CONTENT)
        notificationSetup(context, title, content, CHANNEL_ID, RELEASE_NOTIFICATION_CHANNEL)
    }

    fun setReleaseAlarm(context: Context?, movieUpcomingList: List<ContentUpcomingByDateEntity>) {
        movieUpcomingList.forEach {
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val releaseDate = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }

            RELEASE_REPEATING += 1
            delayNotification += 1
            notificationTimeTrigger = releaseDate.timeInMillis.plus(delayNotification)
            pendingIntent = pendingIntentService(context, it.title, it.overview)
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                notificationTimeTrigger,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    private fun pendingIntentService(
        context: Context?,
        title: String?,
        content: String?
    ): PendingIntent? {
        val intent = Intent(context, ReleaseNotificationReceiver::class.java).apply {
            putExtra(EXTRA_TITLE, "${context?.resources?.getString(R.string.release_Today)} $title")
            putExtra(EXTRA_CONTENT, content)
        }
        return PendingIntent.getBroadcast(
            context,
            RELEASE_REPEATING, intent, 0
        )
    }

    fun cancelAlarmRepeat(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            RELEASE_REPEATING,
            intent,
            0
        )
        alarmManager.cancel(pendingIntent)
    }
}