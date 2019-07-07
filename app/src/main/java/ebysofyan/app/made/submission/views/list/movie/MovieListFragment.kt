package ebysofyan.app.made.submission.views.list.movie

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
import ebysofyan.app.made.submission.data.server.BaseResponse
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.repository.MovieRepository
import ebysofyan.app.made.submission.views.MainActivity
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListFragment : Fragment(), MovieListContract.View {

    private lateinit var adapter: MovieRecyclerViewAdapter
    private lateinit var presenter: MovieListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        if (savedInstanceState != null) {
            adapter.clear()
            adapter.addItems(savedInstanceState.getParcelableArrayList(Constants.MOVIES))
        }

        initPresenter()

        movie_swipe_refresh?.setOnRefreshListener {
            presenter.fetchMovies()
        }
    }

    private fun initPresenter() {
        presenter = MovieListPresenter(context, MovieRepository())
        presenter.attach(this)
        presenter.fetchMovies()
    }

    private fun initRecyclerView() {
        adapter = MovieRecyclerViewAdapter { view, movie, _ ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtras(bundleOf(Constants.PARCELABLE_OBJ to movie))
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
    }

    override fun onLoading(show: Boolean) {
        movie_swipe_refresh?.isRefreshing = show
    }

    override fun onMoviesLoaded(movie: BaseResponse<Movie>?) {
        movie?.results?.let {
            adapter.clear()
            adapter.addItems(it)
        }
    }

    fun refreshItem() {
        presenter.fetchMovies()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.MOVIES, ArrayList(adapter.getItems()))
    }
}
