package c.dicodingmade.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import c.dicodingmade.R
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentmovieupcoming.ContentMovieUpcomingEntity
import c.dicodingmade.repository.ContentMovieUpcomingRepository
import c.dicodingmade.util.notificationSetup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ReleaseNotificationReceiver : BroadcastReceiver() {
    companion object {
        var RELEASE_REPEATING = 110
        var CHANNEL_ID = 123
        const val RELEASE_NOTIFICATION_CHANNEL = "Release Reminder"
    }

    private var title: String? = ""
    private var content: String? = ""

    override fun onReceive(context: Context?, intent: Intent?) {
        val applicationDatabase = ApplicationDatabase.getDatabase(context as Context)
        val contentMovieUpcomingRepository = ContentMovieUpcomingRepository(applicationDatabase)
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(date)
        var list: List<ContentMovieUpcomingEntity>

        CoroutineScope(Dispatchers.IO).launch {
            contentMovieUpcomingRepository.refreshMovieUpcoming()
            list = contentMovieUpcomingRepository.getMovieUpcomingByDate(currentDate)
            if (list.isNotEmpty()) {
                for (i in list.indices) {
                    RELEASE_REPEATING += i
                    CHANNEL_ID += i
                    title = "${context.resources?.getString(R.string.release_Today)} ${list[i].title}"
                    content = list[i].overview
                    notificationSetup(context, title, content, CHANNEL_ID, RELEASE_NOTIFICATION_CHANNEL)
                }
            }
        }
    }

    fun setReleaseAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmSchedule = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val currentDate = Calendar.getInstance()
        val pendingIntent = pendingIntentService(context)

        if (alarmSchedule.before(currentDate)) alarmSchedule.add(Calendar.HOUR_OF_DAY, 24)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmSchedule.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun pendingIntentService(context: Context?): PendingIntent? {
        val intent = Intent(context, ReleaseNotificationReceiver::class.java)
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