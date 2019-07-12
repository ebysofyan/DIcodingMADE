package ebysofyan.app.made.submission.views.search.movie

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
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.views.MainActivity
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity
import ebysofyan.app.made.submission.views.list.movie.MovieRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieSearchListFragment : Fragment() {
    companion object {
        fun getInstance(dataSet: ArrayList<Movie>, isLoading: Boolean): MovieSearchListFragment {
            return MovieSearchListFragment().apply {
                arguments = bundleOf(
                    Constants.SEARCH_DATA_SET to dataSet,
                    Constants.SEARCH_LOADING to isLoading
                )
            }
        }
    }

    private lateinit var adapter: MovieRecyclerViewAdapter
    private var dataSet: ArrayList<Movie>? = null
    private var isLoading: Boolean? = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        adapter = MovieRecyclerViewAdapter { view, favorite, _ ->
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

    fun setSearchResult(movies: MutableList<Movie>) {
        adapter.clear()
        adapter.addItems(movies)
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.MOVIES, ArrayList(adapter.getItems()))
    }
}
