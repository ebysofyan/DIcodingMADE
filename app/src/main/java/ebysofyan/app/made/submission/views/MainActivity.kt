package ebysofyan.app.made.submission.views

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.views.list.movie.MovieListFragment
import ebysofyan.app.made.submission.views.list.tv.TvShowListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 * Created by @ebysofyan on 7/3/19
 */
class MainActivity : AppCompatActivity() {

    private lateinit var tabsAdapter: TabsViewpagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActionBar()
        setupViewPager()
    }

    private fun initActionBar() {
        setSupportActionBar(_toolbar)
        supportActionBar?.apply {
            title = "The Movie DB"
        }
        _toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun setupViewPager() {
        tabsAdapter = TabsViewpagerAdapter(supportFragmentManager)

        tabsAdapter.fragments.add(MovieListFragment() to "Movies")
        tabsAdapter.fragments.add(TvShowListFragment() to "TV Show")

        main_viewpager.adapter = tabsAdapter
        main_tabs.setupWithViewPager(main_viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_language) {
            startActivity(Intent(ACTION_LOCALE_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }
}