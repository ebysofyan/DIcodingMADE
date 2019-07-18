package ebysofyan.app.made.submission.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.extensions.toDateFormat
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.common.utils.CursorUtil
import ebysofyan.app.made.submission.data.local.Favorite
import ebysofyan.app.made.submission.services.provider.ProviderContract


/**
 * Created by @ebysofyan on 7/12/19
 */
class FavoriteStackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var widgetItems: MutableList<Favorite> = mutableListOf()

    private var cursor: Cursor? = null

    override fun onCreate() {}

    override fun onDataSetChanged() {

        val identityToken = Binder.clearCallingIdentity()

        if (cursor != null) {
            cursor?.close()
        }
        cursor =
            context.contentResolver.query(
                ProviderContract.CONTENT_URI,
                null,
                null,
                null,
                null
            )

        cursor?.let {
            widgetItems.clear()
            widgetItems.addAll(CursorUtil.mapCursorToArrayList(it))
        }
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return widgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.movie_list_item)
        val fav = widgetItems[position]
        val imageBitmap =
            Glide.with(context).asBitmap()
                .load(Constants.getImageUrl(fileName = fav.posterPath))
                .submit()
                .get()
        rv.setImageViewBitmap(R.id.movie_item_image, imageBitmap)
        rv.setTextViewText(R.id.movie_item_title, fav.title)
        rv.setTextViewText(R.id.movie_item_release_date, fav.releaseDate.toDateFormat())
        rv.setTextViewText(R.id.movie_item_rating, fav.voteAverage.toString())

        val extras = Bundle()
        extras.putInt(Constants.EXTRA_ITEM, fav.movieId.toInt())
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.movie_item_image, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}