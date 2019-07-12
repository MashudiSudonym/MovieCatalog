package c.dicodingmade.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import c.dicodingmade.R
import c.dicodingmade.database.contentMovieUpcoming.ContentUpcomingByDateEntity
import c.dicodingmade.receiver.DailyNotificationReceiver
import c.dicodingmade.receiver.ReleaseNotificationReceiver
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val settingsViewModel: SettingsViewModel by viewModel()
    private val dailyNotificationReceiver = DailyNotificationReceiver()
    private val releaseNotificationReceiver = ReleaseNotificationReceiver()
    private var title = ""
    private var content = ""

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val chooseLanguage = findPreference<Preference>("choose_language")
        chooseLanguage?.intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "release_reminder" -> {
                when (sharedPreferences?.getBoolean(key, false)) {
                    true -> {
                        settingsViewModel.movies.observe(this, Observer {
                            val movieUpcomingList: ArrayList<ContentUpcomingByDateEntity> = arrayListOf()
                            it.forEach { movieUpcoming ->
                                movieUpcomingList.add(movieUpcoming)
                            }
                            Log.d("MADEALARM", movieUpcomingList.toString())
                            releaseNotificationReceiver.setReleaseAlarm(activity, movieUpcomingList)
                        })
                    }
                    false -> releaseNotificationReceiver.cancelAlarmRepeat(activity)
                }
            }
            "daily_reminder" -> {
                when (sharedPreferences?.getBoolean(key, false)) {
                    true -> {
                        title = resources.getString(R.string.app_name)
                        content = resources.getString(R.string.notif_daily_body)
                        dailyNotificationReceiver.setDailyAlarm(activity, title, content)
                    }
                    false -> dailyNotificationReceiver.cancelAlarm(activity)
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