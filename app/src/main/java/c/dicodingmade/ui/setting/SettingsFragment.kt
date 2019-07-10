package c.dicodingmade.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import c.dicodingmade.R
import c.dicodingmade.work.DailyReminderNotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val dailyDate = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 7)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val chooseLanguage = findPreference<Preference>("choose_language")
        chooseLanguage?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "release_reminder" -> {
                Log.i(
                    "release_reminder",
                    "Preference value was updated to: " + sharedPreferences?.getBoolean(key, false)
                )
            }
            "daily_reminder" -> {
                when (sharedPreferences?.getBoolean(key, false)) {
                    true -> {
                        CoroutineScope(Dispatchers.Default).launch {
                            dailyReminderInit()
                        }
                    }
                    false -> cancelDailyReminderWorker()
                }
            }
        }
    }

    // Worker
    private fun cancelDailyReminderWorker() {
        WorkManager.getInstance().cancelAllWorkByTag(DailyReminderNotificationWorker.TAG)
    }

    private fun dailyReminderInit() {
        val dailyReminderRequest = OneTimeWorkRequestBuilder<DailyReminderNotificationWorker>()
            .setInitialDelay(dailyDate.timeInMillis, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance().enqueue(dailyReminderRequest)
    }

    // SharedPreferences Listener Lifecycle
    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
}