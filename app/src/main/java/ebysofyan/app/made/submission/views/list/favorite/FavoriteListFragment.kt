package ebysofyan.app.made.submission.views.list.favorite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.dao.FavoriteDao
import ebysofyan.app.made.submission.views.MainActivity
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

class FavoriteListFragment : Fragment() {

    companion object {
        fun getInstance(query: String): FavoriteListFragment {
            return FavoriteListFragment().apply {
                arguments = bundleOf(Constants.MOVIE_TYPE to query)
            }
        }
    }

    private lateinit var adapter: FavoriteRecyclerViewAdapter
    private lateinit var type: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getString(Constants.MOVIE_TYPE).toString()

        initRecyclerView()
        if (savedInstanceState != null) {
            adapter.clear()
            adapter.addItems(savedInstanceState.getParcelableArrayList(Constants.MOVIES))
        }

        movie_swipe_refresh?.setOnRefreshListener {
            refreshItem()
        }
    }

    private fun initRecyclerView() {
        adapter = FavoriteRecyclerViewAdapter { view, favorite, _ ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtras(bundleOf(Constants.PARCELABLE_OBJ to favorite))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!,
                view.movie_item_image,
                "image_poster"
            )
            startActivityForResult(intent, MainActivity.LIST_CHANGE_CODE, options.toBundle())
        }
        movie_recycler_view.layoutManager = GridLayoutManager(context, 3)
        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.adapter = adapter

        refreshItem()
    }


    private fun refreshItem() {
        adapter.clear()
        FavoriteDao.getAllFavorite(type)?.let { adapter.addItems(it) }
        movie_swipe_refresh?.isRefreshing = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.MOVIES, ArrayList(adapter.getItems()))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.LIST_CHANGE_CODE && resultCode == Activity.RESULT_OK) {
            refreshItem()
        }
    }
}
