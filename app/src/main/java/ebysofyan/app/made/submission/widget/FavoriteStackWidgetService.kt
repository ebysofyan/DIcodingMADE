package ebysofyan.app.made.submission.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by @ebysofyan on 7/12/19
 */
class FavoriteStackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return FavoriteStackRemoteViewsFactory(applicationContext)
    }
}