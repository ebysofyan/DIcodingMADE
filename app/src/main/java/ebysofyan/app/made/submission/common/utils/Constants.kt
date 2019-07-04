package ebysofyan.app.made.submission.common.utils

object Constants {

    const val API_KEY = "27d588f44884cfc30f97f76d26d9a55a"

    const val TV_SHOWS = "TV_SHOWS"
    const val MOVIES = "MOVIES"
    const val PARCELABLE_OBJ = "PARCELABLE_OBJ"

    fun getImageUrl(size: String = "w185", fileName: String): String {
        if (fileName.startsWith("/")) fileName.replace("/", "")
        return "https://image.tmdb.org/t/p/$size/$fileName"
    }
}