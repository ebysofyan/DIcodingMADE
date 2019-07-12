package ebysofyan.app.made.submission.views

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ebysofyan.app.made.submission.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 * Created by @ebysofyan on 7/3/19
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val LIST_CHANGE_CODE = 101
    }

    private val LOCALE_SETTINGS_CODE = 201

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        setSupportActionBar(_toolbar)

        navController = findNavController(R.id.main_fragment)
        main_bottom_navigation.setupWithNavController(navController)
        _toolbar.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_language) {
            startActivityForResult(Intent(ACTION_LOCALE_SETTINGS), LOCALE_SETTINGS_CODE)
        }
        return super.onOptionsItemSelected(item)
    }
}