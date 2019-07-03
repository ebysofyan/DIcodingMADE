package ebysofyan.app.made.submission.views.list.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.data.Movie
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity
import ebysofyan.app.made.submission.views.list.ListRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListFragment : Fragment(), MovieListContract.View {

    private lateinit var adapter: ListRecyclerViewAdapter
    private lateinit var presenter: MovieListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initPresenter()
    }

    private fun initPresenter() {
        presenter = MovieListPresenter(context!!)
        presenter.attach(this)
        presenter.fetchMovies()
    }

    private fun initRecyclerView() {
        adapter = ListRecyclerViewAdapter { view, movie, _ ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            val bundle = Bundle().apply {
                putParcelable(Constants.MOVIE_OBJ, movie)
            }
            intent.putExtras(bundle)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!,
                view.movie_item_image,
                "image_poster"
            )
            startActivity(intent, options.toBundle())
        }
        movie_recycler_view.layoutManager = GridLayoutManager(context, 3)
        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.adapter = adapter
    }

    override fun onMoviesLoaded(movies: MutableList<Movie>) {
        adapter.addItems(movies)
    }
}
