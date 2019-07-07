package ebysofyan.app.made.submission.views.list.movie

import android.content.Context
import ebysofyan.app.made.submission.base.BaseRetrofitCallback
import ebysofyan.app.made.submission.common.extensions.forceLocale2to1
import ebysofyan.app.made.submission.common.extensions.toast
import ebysofyan.app.made.submission.data.server.BaseResponse
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.data.server.MovieError
import ebysofyan.app.made.submission.repository.MovieRepository
import retrofit2.Call

/**
 * Created by @ebysofyan on 7/2/19
 */
class MovieListPresenter(private val context: Context?, private val repository: MovieRepository) :
    MovieListContract.Presenter {
    private var view: MovieListContract.View? = null
    private var service: Call<BaseResponse<Movie>>? = null

    override fun attach(v: MovieListContract.View) {
        this.view = v
    }

    override fun fetchMovies() {
        view?.onLoading(true)

        val queryMap =
            hashMapOf("language" to context?.forceLocale2to1().toString())
        service = repository.fetchMovies(queryMap, object : BaseRetrofitCallback<BaseResponse<Movie>> {
            override fun onFailure(t: Throwable) {
                view?.onLoading(false)
                context?.toast(t.localizedMessage)
            }

            override fun onResponseSuccess(data: BaseResponse<Movie>?) {
                view?.onLoading(false)
                view?.onMoviesLoaded(data)
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