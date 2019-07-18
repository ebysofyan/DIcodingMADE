package ebysofyan.app.made.submission.services.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import ebysofyan.app.made.submission.dao.FavoriteDao

/**
 * Created by @ebysofyan on 7/12/19
 */
class FavoriteContentProvider : ContentProvider() {

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private fun initializeUriMatching() {
        sUriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.CONTENT_PATH, 0)
        sUriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.CONTENT_PATH + "/#", 1)
    }

    override fun onCreate(): Boolean {
        initializeUriMatching()

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val id = when (sUriMatcher.match(uri)) {
            0 -> {
                if (selection != null) {
                    selectionArgs?.get(0)?.toInt()!!
                } else {
                    ProviderContract.ALL_ITEMS
                }
            }
            1 -> uri.lastPathSegment?.toInt()!!
            UriMatcher.NO_MATCH -> -1
            else -> -1
        }

        return populateCursor(id)
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null

    private fun populateCursor(id: Int): Cursor {
        val matrixCursor = MatrixCursor(ProviderContract.matrixColumns)
        val favorites = when {
            id == ProviderContract.ALL_ITEMS -> {
                FavoriteDao.getAllFavorite()
            }
            id > 0 -> {
                FavoriteDao.getAllFavorite()?.filter { id == id }
            }

            else -> mutableListOf()
        }
        favorites?.forEach { fav ->
            matrixCursor.addRow(
                arrayOf(
                    fav.id,
                    fav.movieId,
                    fav.title,
                    fav.releaseDate,
                    fav.overview,
                    fav.voteAverage,
                    fav.posterPath,
                    fav.backdropPath,
                    fav.type
                )
            )
        }

        return matrixCursor
    }
}