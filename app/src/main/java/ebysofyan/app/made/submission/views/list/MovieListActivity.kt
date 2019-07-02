package ebysofyan.app.made.submission.views.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.data.Movie
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MovieListActivity : AppCompatActivity(), MovieListContract.View {

    private lateinit var adapter: MovieListRecyclerViewAdapter
    private lateinit var presenter: MovieListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        initRecyclerView()
        initPresenter()
        initActionBar()
    }

    private fun initPresenter() {
        presenter = MovieListPresenter(this)
        presenter.attach(this)
        presenter.fetchMovies()
    }

    private fun initActionBar() {
        setSupportActionBar(_toolbar)
        supportActionBar?.apply {
            title = "Movies"
        }
        _toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun initRecyclerView() {
        adapter = MovieListRecyclerViewAdapter { view, movie, _ ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            val bundle = Bundle().apply {
                putParcelable(Constants.MOVIE_OBJ, movie)
            }
            intent.putExtras(bundle)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view.movie_item_image,
                "image_poster"
            )
            startActivity(intent, options.toBundle())
        }
        movie_recycler_view.layoutManager = GridLayoutManager(this, 3)
        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.adapter = adapter
    }

    override fun onMoviesLoaded(movies: MutableList<Movie>) {
        adapter.addItems(movies)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
