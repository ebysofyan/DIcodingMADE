package ebysofyan.app.made.submission.common.utils

import ebysofyan.app.made.submission.BuildConfig

object Constants {

    const val FAVORITE_ITEM_ACTION = "FAVORITE_ITEM_ACTION"
    const val EXTRA_ITEM = "EXTRA_ITEM"
    const val MOVIE_TYPE = "MOVIE_TYPE"
    const val API_KEY = BuildConfig.TMDB_API_KEY

    const val TV_SHOWS = "TV_SHOWS"
    const val MOVIES = "MOVIES"
    const val PARCELABLE_OBJ = "PARCELABLE_OBJ"

    fun getImageUrl(size: String = "w185", fileName: String): String {
        if (fileName.startsWith("/")) fileName.replace("/", "")
        return "https://image.tmdb.org/t/p/$size/$fileName"
    }
}