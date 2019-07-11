package c.dicodingmade.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import c.dicodingmade.R
import c.dicodingmade.receiver.DailyNotificationReceiver

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val dailyNotificationReceiver = DailyNotificationReceiver()

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
                        dailyNotificationReceiver.cancelAlarmRepeat(activity)
                        dailyNotificationReceiver.setAlarmRepeat(activity)
                    }
                    false -> dailyNotificationReceiver.cancelAlarmRepeat(activity)
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