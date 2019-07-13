package ebysofyan.app.made.favorite.views.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ebysofyan.app.made.favorite.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val LIST_CHANGE_CODE = 100
    }

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
            title = getString(R.string.app_name)
        }
    }

    private fun setupViewPager() {
        tabsAdapter = TabsViewpagerAdapter(supportFragmentManager)

        tabsAdapter.fragments.add(FavoriteListFragment.getInstance("movie") to getString(R.string.tab_movies_title))
        tabsAdapter.fragments.add(FavoriteListFragment.getInstance("tv-show") to getString(R.string.tab_tv_show_title))

        favorite_viewpager.adapter = tabsAdapter
        favorite_tabs.setupWithViewPager(favorite_viewpager)
    }
}
