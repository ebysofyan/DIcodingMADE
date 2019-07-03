package ebysofyan.app.made.submission.views.list.tv

import ebysofyan.app.made.submission.base.BasePresenter
import ebysofyan.app.made.submission.data.Movie

/**
 * Created by @ebysofyan on 7/2/19
 */
interface TvShowListContract {
    interface View {
        fun onTvShowLoaded(movies: MutableList<Movie>)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchTvShows()
    }
}