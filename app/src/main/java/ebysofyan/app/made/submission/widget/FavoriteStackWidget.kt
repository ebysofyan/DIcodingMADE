package ebysofyan.app.made.submission.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.os.bundleOf
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.dao.FavoriteDao
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity


/**
 * Implementation of App Widget functionality.
 */
class FavoriteStackWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            val intent = Intent(context, FavoriteStackWidgetService::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val views = RemoteViews(context.packageName, R.layout.favorite_widget)
            views.setRemoteAdapter(R.id.widget_stack_view, intent)
            views.setEmptyView(R.id.widget_stack_view, R.id.widget_empty_text)

            val toastIntent = Intent(context, FavoriteStackWidget::class.java)
            toastIntent.action = Constants.FAVORITE_ITEM_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent =
                PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.widget_stack_view, toastPendingIntent)

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_stack_view)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null) {
            if (intent.action == Constants.FAVORITE_ITEM_ACTION) {
                val movieId = intent.getIntExtra(Constants.EXTRA_ITEM, 0)

                val detailIntent =
                    Intent(context, MovieDetailActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                detailIntent.putExtras(bundleOf(Constants.PARCELABLE_OBJ to FavoriteDao.getAllFavorite()?.find { it.movieId == movieId.toLong() }))
                context.startActivity(detailIntent)
            }
        }
        super.onReceive(context, intent)
    }
}

