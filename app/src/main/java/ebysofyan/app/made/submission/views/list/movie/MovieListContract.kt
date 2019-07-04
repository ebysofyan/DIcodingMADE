package ebysofyan.app.made.submission.views.list.movie

import ebysofyan.app.made.submission.base.BasePresenter
import ebysofyan.app.made.submission.data.BaseResponse
import ebysofyan.app.made.submission.data.Movie

/**
 * Created by @ebysofyan on 7/2/19
 */
interface MovieListContract {
    interface View {
        fun onLoading(show: Boolean)
        fun onMoviesLoaded(movie: BaseResponse<Movie>?)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchMovies()
    }
}