package ebysofyan.app.made.submission.common.utils

import android.database.Cursor
import ebysofyan.app.made.submission.data.local.Favorite


object CursorUtil {
    fun mapCursorToArrayList(cursor: Cursor): MutableList<Favorite> {
        val favorites = mutableListOf<Favorite>()

        while (cursor.moveToNext()) {
            favorites.add(
                Favorite(
                    id = cursor.getString(cursor.getColumnIndexOrThrow("id")).toLong(),
                    movieId = cursor.getString(cursor.getColumnIndexOrThrow("movie_id")).toLong(),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    releaseDate = cursor.getString(cursor.getColumnIndexOrThrow("release_date")),
                    voteAverage = cursor.getString(cursor.getColumnIndexOrThrow("vote_average")).toDouble(),
                    posterPath = cursor.getString(cursor.getColumnIndexOrThrow("poster_path")),
                    backdropPath = cursor.getString(cursor.getColumnIndexOrThrow("backdrop_path")),
                    type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                )
            )
        }

        return favorites
    }
}