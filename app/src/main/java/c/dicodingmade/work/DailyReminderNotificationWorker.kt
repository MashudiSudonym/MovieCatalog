package c.dicodingmade.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import c.dicodingmade.R
import c.dicodingmade.ui.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit

class DailyReminderNotificationWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val TAG = "c.dicodingmade.work.DailyReminderNotificationWorker"
        const val NOTIFICATION_CHANNEL = "Daily Reminder"
        const val CHANNEL_ID = 0
    }

    override suspend fun doWork(): Result {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationBuilder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(applicationContext.resources.getString(R.string.app_name))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(applicationContext.resources.getString(R.string.notif_daily_body))
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        return try {
            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    NOTIFICATION_CHANNEL,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    setShowBadge(true)
                    canShowBadge()
                    lockscreenVisibility = 1
                }
                val notificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.createNotificationChannel(channel)
            }

            with(NotificationManagerCompat.from(applicationContext)) {
                notify(CHANNEL_ID, notificationBuilder.build())
            }

            val dailyDate = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 7)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
            val dailyReminderRequest = OneTimeWorkRequestBuilder<DailyReminderNotificationWorker>()
                .setInitialDelay(dailyDate.timeInMillis, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance().enqueue(dailyReminderRequest)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}