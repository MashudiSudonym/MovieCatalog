package c.dicodingmade.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import c.dicodingmade.R
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
                    // show toolbar main in main fragment
                    toolbar_main.visible()

                    // Enable navigation controller like back button in toolbar layout
                    toolbar_main.apply {
                        setupWithNavController(navController, appBarConfiguration)
                    }
                    setSupportActionBar(toolbar_main) // add this for show menu item
                }
                R.id.detailFragment -> {
                    // hide toolbar main in detail fragment
                    toolbar_main.gone()
                }
                else -> setupActionBarWithNavController(navController)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.language_setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_choose_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.menu_favorite -> Toast.makeText(this, "Favorite Menu", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}
