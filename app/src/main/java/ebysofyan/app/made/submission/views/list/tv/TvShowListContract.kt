package ebysofyan.app.made.submission.views.list.tv

import ebysofyan.app.made.submission.base.BasePresenter
import ebysofyan.app.made.submission.data.server.BaseResponse
import ebysofyan.app.made.submission.data.server.TvShow

/**
 * Created by @ebysofyan on 7/2/19
 */
interface TvShowListContract {
    interface View {
        fun onLoading(show: Boolean)
        fun onTvShowLoaded(tvShow: BaseResponse<TvShow>?)
    }

    interface Presenter : BasePresenter<View> {
        fun fetchTvShows()
    }
}