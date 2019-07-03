package ebysofyan.app.made.submission.views.list.movie

import android.content.Context
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.data.Movie
import ebysofyan.app.made.submission.views.list.movie.MovieListContract

/**
 * Created by @ebysofyan on 7/2/19
 */
class MovieListPresenter(private val context: Context) : MovieListContract.Presenter {
    private var view: MovieListContract.View? = null

    override fun attach(v: MovieListContract.View) {
        this.view = v
    }

    override fun detach() {
        this.view = null
    }

    override fun fetchMovies() {
        val movies = mutableListOf<Movie>()

        val titles = context.resources.getStringArray(R.array.movies_titles)
        val releaseDates = context.resources.getStringArray(R.array.movies_release_dates)
        val descriptions = context.resources.getStringArray(R.array.movies_descriptions)
        val posters = context.resources.obtainTypedArray(R.array.movies_posters)
        val ratings = context.resources.getStringArray(R.array.movies_ratings)
        val trailers = context.resources.getStringArray(R.array.movies_trailers)

        repeat(titles.size) { i ->
            val movie = Movie(
                titles[i],
                releaseDates[i],
                descriptions[i],
                posters.getResourceId(i, -1),
                ratings[i],
                trailers[i]
            )
            movies.add(movie)
        }

        view?.onMoviesLoaded(movies)
    }
}