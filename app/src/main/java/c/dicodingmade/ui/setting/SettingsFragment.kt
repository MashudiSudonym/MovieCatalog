package c.dicodingmade.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import c.dicodingmade.R
import c.dicodingmade.receiver.DailyNotificationReceiver
import c.dicodingmade.receiver.ReleaseNotificationReceiver

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val dailyNotificationReceiver = DailyNotificationReceiver()
    private val releaseNotificationReceiver = ReleaseNotificationReceiver()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val chooseLanguage = findPreference<Preference>("choose_language")
        chooseLanguage?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "release_reminder" -> {
                when (sharedPreferences?.getBoolean(key, false)) {
                    true -> releaseNotificationReceiver.setReleaseAlarm(activity)
                    false -> releaseNotificationReceiver.cancelAlarmRepeat(activity)
                }
            }
            "daily_reminder" -> {
                when (sharedPreferences?.getBoolean(key, false)) {
                    true -> dailyNotificationReceiver.setDailyAlarm(activity)
                    false -> dailyNotificationReceiver.cancelAlarmDaily(activity)
                }
            }
        }
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