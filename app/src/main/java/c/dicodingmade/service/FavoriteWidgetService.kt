package c.dicodingmade.service

import android.content.Intent
import android.widget.RemoteViewsService
import c.dicodingmade.ui.widget.StackRemoteViewsFactory

class FavoriteWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}