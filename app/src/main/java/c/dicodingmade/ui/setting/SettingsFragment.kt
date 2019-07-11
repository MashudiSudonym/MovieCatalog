package c.dicodingmade.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.edit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import c.dicodingmade.R
import c.dicodingmade.receiver.DailyNotificationReceiver
import c.dicodingmade.receiver.ReleaseNotificationReceiver
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val dailyNotificationReceiver = DailyNotificationReceiver()
    private val releaseNotificationReceiver = ReleaseNotificationReceiver()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(date)
        settingsViewModel.movieUpcomingByDate(currentDate)
        val movieTodayTitle = settingsViewModel.movieUpcomingToday?.title
        val movieTodayOverview = settingsViewModel.movieUpcomingToday?.overview
        preferenceManager.sharedPreferences.edit {
            putString(getString(R.string.shared_pref_movie_title), movieTodayTitle)
            putString(getString(R.string.shared_pref_movie_overview), movieTodayOverview)
            apply()
        }

        val chooseLanguage = findPreference<Preference>("choose_language")
        chooseLanguage?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {


        when (key) {
            "release_reminder" -> {
                when (sharedPreferences?.getBoolean(key, false)) {
                    true -> {
                        releaseNotificationReceiver.cancelAlarmRepeat(activity)
                        releaseNotificationReceiver.setAlarmRepeat(activity)
                    }
                    false -> releaseNotificationReceiver.cancelAlarmRepeat(activity)
                }
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