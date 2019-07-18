package ebysofyan.app.made.submission.common.utils

import ebysofyan.app.made.submission.BuildConfig

object Constants {

    const val NOTIFICATION_DATA = "NOTIFICATION_DATA"
    val ALARM_MESSAGE_TITLE = "ALARM_MESSAGE_TITLE"
    val ALARM_MESSAGE_BODY = "ALARM_MESSAGE_BODY"
    const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    const val EXTRA_TYPE = "EXTRA_TYPE"
    const val SEARCH_LOADING = "SEARCH_LOADING"
    const val SEARCH_DATA_SET = "SEARCH_DATA_SET"
    const val MOVIE_QUERY = "MOVIE_QUERY"
    const val FAVORITE_ITEM_ACTION = "FAVORITE_ITEM_ACTION"
    const val EXTRA_ITEM = "EXTRA_ITEM"
    const val MOVIE_TYPE = "MOVIE_TYPE"
    const val API_KEY = BuildConfig.TMDB_API_KEY

    const val TV_SHOWS = "TV_SHOWS"
    const val MOVIES = "MOVIES"
    const val PARCELABLE_OBJ = "PARCELABLE_OBJ"

    fun getImageUrl(size: String = "w185", fileName: String?): String {
        if (fileName?.startsWith("/") == true) fileName.replace("/", "")
        return "https://image.tmdb.org/t/p/$size/$fileName"
    }
}