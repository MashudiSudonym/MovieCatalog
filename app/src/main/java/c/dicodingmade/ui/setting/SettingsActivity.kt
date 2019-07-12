package c.dicodingmade.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import c.dicodingmade.R
import kotlinx.android.synthetic.main.settings_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {
    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()

        setSupportActionBar(toolbar_setting)
        supportActionBar?.apply {
            title = resources.getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        settingsViewModel.contentList.observe(this, Observer {
            settingsViewModel.getContent(it)
        })
    }
}