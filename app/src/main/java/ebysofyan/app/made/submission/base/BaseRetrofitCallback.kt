package ebysofyan.app.made.submission.base

import ebysofyan.app.made.submission.data.server.MovieError

/**
 * Created by @ebysofyan on 7/4/19
 */

interface BaseRetrofitCallback<T> {
    fun onFailure(t: Throwable)
    fun onResponseSuccess(data: T?)
    fun onResponseError(httpStatusCode: Int, error: MovieError?)
}