package ebysofyan.app.made.submission.views.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.extensions.hideKeyboard
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.data.server.TvShow
import ebysofyan.app.made.submission.repository.MovieRepository
import ebysofyan.app.made.submission.views.TabsViewpagerAdapter
import ebysofyan.app.made.submission.views.search.movie.MovieSearchListFragment
import ebysofyan.app.made.submission.views.search.tv.TvShowSearchListFragment
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_movie_list.*

/**
 * Created by @ebysofyan on 7/12/19
 */
class MovieSearchActivity : AppCompatActivity(), MovieSearchContract.View {

    private lateinit var tabsAdapter: TabsViewpagerAdapter
    private lateinit var presenter: MovieSearchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initPresenter()
        initActionBar()
        initActionListener()
        setupViewPager()
    }

    private fun initPresenter() {
        presenter = MovieSearchPresenter(this, MovieRepository())
        presenter.attach(this)
    }

    private fun initActionBar() {
        setSupportActionBar(_toolbar)
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        _toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initActionListener() {
        search_text_input.isCursorVisible = false
        search_text_input.setOnClickListener {
            search_text_input.isCursorVisible = true
        }

        search_image_magnify.setOnClickListener {
            presenter.searchMulti(search_text_input.text.toString())
            hideKeyboard()
        }

        search_text_input.setOnEditorActionListener { v, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    presenter.searchMulti(v.text.toString())

                    hideKeyboard()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setupViewPager() {
        tabsAdapter = TabsViewpagerAdapter(supportFragmentManager)

        tabsAdapter.fragments.add(
            MovieSearchListFragment.getInstance(
                ArrayList(mutableListOf<Movie>()),
                false
            ) to getString(R.string.tab_movies_title)
        )
        tabsAdapter.fragments.add(
            TvShowSearchListFragment.getInstance(
                ArrayList(mutableListOf<TvShow>()),
                false
            ) to getString(R.string.tab_tv_show_title)
        )

        search_viewpager.adapter = tabsAdapter
        search_tabs.setupWithViewPager(search_viewpager)
    }

    override fun onLoading(show: Boolean) {
        (supportFragmentManager.fragments[0] as MovieSearchListFragment).movie_swipe_refresh?.isRefreshing = show
        (supportFragmentManager.fragments[1] as TvShowSearchListFragment).movie_swipe_refresh?.isRefreshing = show
    }

    override fun onMultiSearchLoaded(jsonElement: JsonElement?) {
        val responses = jsonElement?.asJsonObject?.get("results")?.asJsonArray
        val moviesElement = responses?.filter { it.asJsonObject.get("media_type").asString == "movie" }
        val tvsElement = responses?.filter { it.asJsonObject.get("media_type").asString == "tv" }

        val movies = if (moviesElement?.isNotEmpty() == true) parseJsonElementToMovies(ArrayList(moviesElement))
        else mutableListOf()

        val tvs = if (tvsElement?.isNotEmpty() == true) parseJsonElementToTvShows(ArrayList(tvsElement))
        else mutableListOf()

        (supportFragmentManager.fragments[0] as MovieSearchListFragment).setSearchResult(movies)
        (supportFragmentManager.fragments[1] as TvShowSearchListFragment).setSearchResult(tvs)
    }

    private fun parseJsonElementToMovies(moviesElement: MutableList<JsonElement>): MutableList<Movie> {
        val movies = mutableListOf<Movie>()
        moviesElement.forEach { mve ->
            movies.add(Gson().fromJson(mve, Movie::class.java))
        }
        return movies
    }

    private fun parseJsonElementToTvShows(tvsElement: MutableList<JsonElement>): MutableList<TvShow> {
        val tvs = mutableListOf<TvShow>()
        tvsElement.forEach { tve ->
            tvs.add(Gson().fromJson(tve, TvShow::class.java))
        }
        return tvs
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}