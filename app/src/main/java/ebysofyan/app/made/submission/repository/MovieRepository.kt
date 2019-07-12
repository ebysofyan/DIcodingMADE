package ebysofyan.app.made.submission.repository

import com.google.gson.JsonElement
import ebysofyan.app.made.submission.base.BaseRetrofitCallback
import ebysofyan.app.made.submission.common.utils.ErrorBodyParser
import ebysofyan.app.made.submission.common.utils.NetworkConfig
import ebysofyan.app.made.submission.data.server.BaseResponse
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.data.server.MovieError
import ebysofyan.app.made.submission.data.server.TvShow
import ebysofyan.app.made.submission.repository.services.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by @ebysofyan on 7/4/19
 */
class MovieRepository {
    fun fetchMovies(
        queryMap: HashMap<String, String> = hashMapOf(),
        callback: BaseRetrofitCallback<BaseResponse<Movie>>
    ): Call<BaseResponse<Movie>> {
        val service = NetworkConfig.client.create(MovieService::class.java).fetchMovies(queryMap = queryMap)
        service.enqueue(object : Callback<BaseResponse<Movie>> {
            override fun onFailure(call: Call<BaseResponse<Movie>>, t: Throwable) {
                callback.onFailure(t)
            }

            override fun onResponse(call: Call<BaseResponse<Movie>>, response: Response<BaseResponse<Movie>>) {
                when {
                    response.isSuccessful -> callback.onResponseSuccess(response.body())
                    else -> callback.onResponseError(
                        response.code(),
                        ErrorBodyParser.parse(response.errorBody(), MovieError::class.java)
                    )
                }
            }

        })

        return service
    }

    fun fetchTvShow(
        queryMap: HashMap<String, String> = hashMapOf(),
        callback: BaseRetrofitCallback<BaseResponse<TvShow>>
    ): Call<BaseResponse<TvShow>> {
        val service = NetworkConfig.client.create(MovieService::class.java).fetchTvShow(queryMap = queryMap)
        service.enqueue(object : Callback<BaseResponse<TvShow>> {
            override fun onFailure(call: Call<BaseResponse<TvShow>>, t: Throwable) {
                callback.onFailure(t)
            }

            override fun onResponse(call: Call<BaseResponse<TvShow>>, response: Response<BaseResponse<TvShow>>) {
                when {
                    response.isSuccessful -> callback.onResponseSuccess(response.body())
                    else -> callback.onResponseError(
                        response.code(),
                        ErrorBodyParser.parse(response.errorBody(), MovieError::class.java)
                    )
                }
            }

        })

        return service
    }

    fun searchMulti(
        queryMap: HashMap<String, String> = hashMapOf(),
        callback: BaseRetrofitCallback<JsonElement>
    ): Call<JsonElement> {
        val service = NetworkConfig.client.create(MovieService::class.java).searchMulti(queryMap = queryMap)
        service.enqueue(object : Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                callback.onFailure(t)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                when {
                    response.isSuccessful -> callback.onResponseSuccess(response.body())
                    else -> callback.onResponseError(
                        response.code(),
                        ErrorBodyParser.parse(response.errorBody(), MovieError::class.java)
                    )
                }
            }

        })

        return service
    }
}