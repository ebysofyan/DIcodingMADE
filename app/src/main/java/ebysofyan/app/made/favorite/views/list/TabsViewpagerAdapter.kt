package ebysofyan.app.made.favorite.views.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabsViewpagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val fragments = mutableListOf<Pair<Fragment, String>>()

    override fun getItem(position: Int): Fragment = fragments[position].first

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = fragments[position].second
}