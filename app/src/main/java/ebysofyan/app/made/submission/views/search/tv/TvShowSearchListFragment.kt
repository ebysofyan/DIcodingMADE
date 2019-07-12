package ebysofyan.app.made.submission.views.search.tv

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
import ebysofyan.app.made.submission.data.server.TvShow
import ebysofyan.app.made.submission.views.MainActivity
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity
import ebysofyan.app.made.submission.views.list.tv.TvShowRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

class TvShowSearchListFragment : Fragment() {

    companion object {
        fun getInstance(dataSet: ArrayList<TvShow>, isLoading: Boolean): TvShowSearchListFragment {
            return TvShowSearchListFragment().apply {
                arguments = bundleOf(
                    Constants.SEARCH_DATA_SET to dataSet,
                    Constants.SEARCH_LOADING to isLoading
                )
            }
        }
    }

    private lateinit var adapter: TvShowRecyclerViewAdapter
    private var dataSet: ArrayList<TvShow>? = null
    private lateinit var type: String
    private var isLoading: Boolean? = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getString(Constants.MOVIE_TYPE).toString()
        dataSet = arguments?.getParcelableArrayList(Constants.SEARCH_DATA_SET)
        isLoading = arguments?.getBoolean(Constants.SEARCH_LOADING, false)

        initRecyclerView()
        if (savedInstanceState != null) {
            adapter.clear()
            adapter.addItems(savedInstanceState.getParcelableArrayList(Constants.MOVIES))
        }

        movie_swipe_refresh?.setOnRefreshListener {
            movie_swipe_refresh.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        adapter = TvShowRecyclerViewAdapter { view, favorite, _ ->
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
        dataSet?.let { adapter.addItems(it) }
        movie_swipe_refresh?.isRefreshing = false
    }

    fun setSearchResult(tvShow: MutableList<TvShow>) {
        adapter.clear()
        adapter.addItems(tvShow)
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.MOVIES, ArrayList(adapter.getItems()))
    }
}
