package ebysofyan.app.made.submission.views.search

import android.content.Context
import com.google.gson.JsonElement
import ebysofyan.app.made.submission.base.BaseRetrofitCallback
import ebysofyan.app.made.submission.common.extensions.forceLocale2to1
import ebysofyan.app.made.submission.common.extensions.toast
import ebysofyan.app.made.submission.data.server.MovieError
import ebysofyan.app.made.submission.repository.MovieRepository
import retrofit2.Call

/**
 * Created by @ebysofyan on 7/13/19
 */
class MovieSearchPresenter(private val context: Context?, private val repository: MovieRepository) :
    MovieSearchContract.Presenter {

    private var view: MovieSearchContract.View? = null
    private var request: Call<JsonElement>? = null

    override fun searchMulti(query: String) {
        view?.onLoading(true)

        val queryMap =
            hashMapOf("language" to context?.forceLocale2to1().toString(), "query" to query)

        request = repository.searchMulti(queryMap, object : BaseRetrofitCallback<JsonElement> {
            override fun onFailure(t: Throwable) {
                view?.onLoading(false)
                context?.toast(t.localizedMessage)
            }

            override fun onResponseSuccess(data: JsonElement?) {
                view?.onLoading(false)
                view?.onMultiSearchLoaded(data)
            }

            override fun onResponseError(httpStatusCode: Int, error: MovieError?) {
                view?.onLoading(false)
                context?.toast(error?.statusMessage.toString())
            }

        })
    }

    override fun attach(v: MovieSearchContract.View) {
        this.view = v
    }

    override fun detach() {
        request?.cancel()
    }
}