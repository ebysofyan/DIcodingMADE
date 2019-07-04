package ebysofyan.app.made.submission.views.list.tv

import android.content.Context
import ebysofyan.app.made.submission.base.BaseRetrofitCallback
import ebysofyan.app.made.submission.common.extensions.forceLocale2to1
import ebysofyan.app.made.submission.common.extensions.toast
import ebysofyan.app.made.submission.data.BaseResponse
import ebysofyan.app.made.submission.data.MovieError
import ebysofyan.app.made.submission.data.TvShow
import ebysofyan.app.made.submission.repository.MovieRepository
import retrofit2.Call

/**
 * Created by @ebysofyan on 7/2/19
 */
class TvShowListPresenter(private val context: Context?, private val repository: MovieRepository) :
    TvShowListContract.Presenter {
    private var view: TvShowListContract.View? = null
    private var service: Call<BaseResponse<TvShow>>? = null

    override fun attach(v: TvShowListContract.View) {
        this.view = v
    }

    override fun fetchTvShows() {
        view?.onLoading(true)

        val queryMap = hashMapOf("language" to context?.forceLocale2to1().toString())
        service = repository.fetchTvShow(queryMap, object : BaseRetrofitCallback<BaseResponse<TvShow>> {
            override fun onFailure(t: Throwable) {
                view?.onLoading(false)
                context?.toast(t.localizedMessage)
            }

            override fun onResponseSuccess(data: BaseResponse<TvShow>?) {
                view?.onLoading(false)
                view?.onTvShowLoaded(data)
            }

            override fun onResponseError(httpStatusCode: Int, error: MovieError?) {
                view?.onLoading(false)
                context?.toast(error?.statusMessage.toString())
            }
        })
    }

    override fun detach() {
        this.view = null
        service?.cancel()
    }
}