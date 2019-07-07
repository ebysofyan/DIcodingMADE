package ebysofyan.app.made.submission.repository.services

import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.data.server.BaseResponse
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.data.server.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by @ebysofyan on 7/4/19
 */
interface MovieService {
    @GET("/3/discover/movie")
    fun fetchMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @QueryMap queryMap: HashMap<String, String>
    ): Call<BaseResponse<Movie>>

    @GET("/3/discover/tv")
    fun fetchTvShow(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @QueryMap queryMap: HashMap<String, String>
    ): Call<BaseResponse<TvShow>>
}