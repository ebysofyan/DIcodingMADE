package ebysofyan.app.made.submission.views.list.tv

import android.content.Context
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.data.Movie

/**
 * Created by @ebysofyan on 7/2/19
 */
class TvShowListPresenter(private val context: Context) : TvShowListContract.Presenter {
    private var view: TvShowListContract.View? = null

    override fun attach(v: TvShowListContract.View) {
        this.view = v
    }

    override fun detach() {
        this.view = null
    }

    override fun fetchTvShows() {
        val movies = mutableListOf<Movie>()

        val titles = context.resources.getStringArray(R.array.tv_show_titles)
        val releaseDates = context.resources.getStringArray(R.array.tv_show_release_dates)
        val descriptions = context.resources.getStringArray(R.array.tv_show_descriptions)
        val posters = context.resources.obtainTypedArray(R.array.tv_show_posters)
        val ratings = context.resources.getStringArray(R.array.tv_show_ratings)
        val trailers = context.resources.getStringArray(R.array.tv_show_trailers)

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

        view?.onTvShowLoaded(movies)
    }
}