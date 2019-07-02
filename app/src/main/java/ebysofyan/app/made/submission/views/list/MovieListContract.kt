package ebysofyan.app.made.submission.views.list

import ebysofyan.app.made.submission.base.BasePresenter
import ebysofyan.app.made.submission.data.Movie

/**
 * Created by @ebysofyan on 7/2/19
 */
interface MovieListContract {
    interface View {
        fun onMoviesLoaded(movies: MutableList<Movie>)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchMovies()
    }
}