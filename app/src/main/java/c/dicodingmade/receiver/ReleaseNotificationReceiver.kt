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
        const val ID_REPEATING = 110
        const val CHANNEL_ID = 123
        const val NOTIFICATION_CHANNEL = "Release Reminder"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPref =
            context?.getSharedPreferences(context.resources?.getString(R.string.shared_pref), Context.MODE_PRIVATE)
        val titleMovie = sharedPref?.getString(context.resources?.getString(R.string.shared_pref_movie_title), "")
        val overviewMovie = sharedPref?.getString(context.resources?.getString(R.string.shared_pref_movie_overview), "")
        val title = "${context?.resources?.getString(R.string.release_Today)} $titleMovie"
        notificationSetup(context, title, overviewMovie, CHANNEL_ID, NOTIFICATION_CHANNEL)
    }

    fun setAlarmRepeat(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val releaseDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val pendingIntent = pendingIntentService(context)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            releaseDate.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun pendingIntentService(
        context: Context?
    ): PendingIntent? {
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