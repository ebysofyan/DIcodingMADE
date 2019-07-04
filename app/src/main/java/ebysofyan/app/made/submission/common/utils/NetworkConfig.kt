package ebysofyan.app.made.submission.common.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by @ebysofyan on 6/13/19
 */
object NetworkConfig {

    private val createOkHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    const val BASE_URL = "https://api.themoviedb.org"

    val client: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}