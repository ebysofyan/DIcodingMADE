package ebysofyan.app.made.submission.views.list.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.views.TabsViewpagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * Created by @ebysofyan on 7/8/19
 */
class FavoriteFragment : Fragment() {

    private lateinit var tabsAdapter: TabsViewpagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        tabsAdapter = TabsViewpagerAdapter(childFragmentManager)

        tabsAdapter.fragments.add(FavoriteListFragment.getInstance("movie") to getString(R.string.tab_movies_title))
        tabsAdapter.fragments.add(FavoriteListFragment.getInstance("tv-show") to getString(R.string.tab_tv_show_title))

        favorite_viewpager.adapter = tabsAdapter
        favorite_tabs.setupWithViewPager(favorite_viewpager)
    }
}