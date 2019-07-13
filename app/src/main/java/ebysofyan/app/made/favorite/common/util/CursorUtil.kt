package ebysofyan.app.made.favorite.common.util

import android.database.Cursor
import ebysofyan.app.made.favorite.data.Favorite


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
                    overview = cursor.getString(cursor.getColumnIndexOrThrow("overview")),
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