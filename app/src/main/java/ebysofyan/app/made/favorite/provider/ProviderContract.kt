package ebysofyan.app.made.favorite.provider

import android.net.Uri

object ProviderContract {
    val matrixColumns =
        arrayOf(
            "id",
            "movie_id",
            "title",
            "release_date",
            "overview",
            "vote_average",
            "poster_path",
            "backdrop_path",
            "type"
        )

    val AUTHORITY = "ebysofyan.app.made.submission.provider"
    val CONTENT_PATH = "favorite"
    val CONTENT_URI = Uri.parse("content://$AUTHORITY/$CONTENT_PATH")
    val ALL_ITEMS = -2
    val WORD_ID = "id"
}