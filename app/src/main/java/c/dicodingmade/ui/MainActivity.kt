package c.dicodingmade.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import c.dicodingmade.R
import c.dicodingmade.ui.setting.SettingsActivity
import c.dicodingmade.util.gone
import c.dicodingmade.util.visible
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
                R.id.favoriteFragment -> {
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
            R.id.menu_search -> Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show()
            R.id.favoriteFragment -> item.onNavDestinationSelected(navController)
            R.id.menu_setting -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
