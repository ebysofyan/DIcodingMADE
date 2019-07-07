package ebysofyan.app.made.submission.views

import android.app.Activity
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
    private val LOCALE_SETTINGS_CODE = 201

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

        tabsAdapter.fragments.add(MovieListFragment() to getString(R.string.tab_movies_title))
        tabsAdapter.fragments.add(TvShowListFragment() to getString(R.string.tab_tv_show_title))

        main_viewpager.adapter = tabsAdapter
        main_tabs.setupWithViewPager(main_viewpager)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCALE_SETTINGS_CODE && resultCode == Activity.RESULT_OK) {
            when (val activeFragment = tabsAdapter.getItem(main_viewpager.currentItem)) {
                is MovieListFragment -> {
                    activeFragment.refreshItem()
                }

                is TvShowListFragment -> {
                    activeFragment.refreshItem()
                }
            }
        }
    }
}