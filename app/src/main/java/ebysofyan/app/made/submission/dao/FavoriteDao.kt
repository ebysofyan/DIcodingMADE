package ebysofyan.app.made.submission.dao

import ebysofyan.app.made.submission.App
import ebysofyan.app.made.submission.data.local.Favorite
import ebysofyan.app.made.submission.data.local.Favorite_

/**
 * Created by @ebysofyan on 7/8/19
 */
object FavoriteDao {
    val boxStore = App.boxStore
    val favoriteStore = boxStore?.boxFor(Favorite::class.java)

    fun isExist(id: Long) = favoriteStore?.query()?.equal(Favorite_.movieId, id)?.build()?.let {
        return@let it.count() > 0
    } ?: false

    fun getAllFavorite(type: String) = favoriteStore?.query()?.equal(Favorite_.type, type)?.build()?.find()

    fun addToFavorite(favorite: Favorite, onSuccess: () -> Unit = {}) {
        boxStore?.runInTx {
            favoriteStore?.put(favorite).run {
                onSuccess.invoke()
            }
        }
    }

    fun deleteFromFavorite(id: Long, onSuccess: () -> Unit = {}) {
        boxStore?.runInTx {
            favoriteStore?.query()?.equal(Favorite_.movieId, id)?.build()?.remove().run {
                onSuccess.invoke()
            }
        }
    }
}