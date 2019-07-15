package c.m.dicodingmadefavorite.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import c.m.dicodingmadefavorite.R
import c.m.dicodingmadefavorite.util.gone
import c.m.dicodingmadefavorite.util.visible
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> {
                    mainToolbarSetup()
                }
                R.id.detailFragment -> {
                    // hide toolbar main in detail fragment
                    hideMainToolbar()
                }
                else -> setupActionBarWithNavController(navController)
            }
        }
    }

    private fun hideMainToolbar() {
        toolbar_main.gone()
    }

    private fun mainToolbarSetup() {
        // show toolbar main in main fragment
        toolbar_main.visible()

        // Enable navigation controller like back button in toolbar layout
        toolbar_main.apply {
            setupWithNavController(navController, appBarConfiguration)
        }
        setSupportActionBar(toolbar_main) // add this for show menu item
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_choose_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }
}