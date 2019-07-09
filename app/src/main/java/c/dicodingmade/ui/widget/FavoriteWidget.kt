package c.dicodingmade.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import c.dicodingmade.R
import c.dicodingmade.service.FavoriteWidgetService

class FavoriteWidget : AppWidgetProvider() {
    companion object {
        const val TOAST_ACTION = "c.dicodingmade.TOAST_ACTION"
        const val EXTRA_ITEM = "c.dicodingmade.EXTRA_ITEM"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds.forEach {
            val intent = Intent(context, FavoriteWidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, it)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }
            val remoteViews = RemoteViews(context.packageName, R.layout.favorite_widget).apply {
                setRemoteAdapter(R.id.stack_view, intent)
                setEmptyView(R.id.stack_view, R.id.empty_view)
            }
            val toastPendingIntent = Intent(context, FavoriteWidget::class.java).run {
                action = TOAST_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, it)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
                PendingIntent.getBroadcast(context, 0, this, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            remoteViews.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
            appWidgetManager.updateAppWidget(it, remoteViews)
            appWidgetManager.notifyAppWidgetViewDataChanged(it, R.id.stack_view)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == TOAST_ACTION) {
            val appWidgetId =
                intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
            val appWidgetManager = AppWidgetManager.getInstance(context.applicationContext)
            val thisWidget = ComponentName(context, FavoriteWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)

            Toast.makeText(context, "$viewIndex, $appWidgetId $appWidgetIds", Toast.LENGTH_SHORT).show()
        }
    }
}