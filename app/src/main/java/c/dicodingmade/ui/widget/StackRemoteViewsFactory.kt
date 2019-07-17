package c.dicodingmade.ui.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import c.dicodingmade.BuildConfig
import c.dicodingmade.R
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.favorite.FavoriteEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    companion object {
        const val EXTRA_ITEM = "EXTRA_ITEM"
    }

    private lateinit var favoriteList: ArrayList<FavoriteEntity>

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = favoriteList[position].id.toLong()

    override fun onDataSetChanged() {
        favoriteList =
            ApplicationDatabase.getDatabase(context).favoriteDao().readFavorite() as ArrayList<FavoriteEntity>
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        return RemoteViews(context.packageName, R.layout.item_favorite_widget).apply {
            try {
                val imageBitmap = Glide.with(context)
                    .asBitmap()
                    .load(BuildConfig.BASE_URL_POSTER + favoriteList[position].backdropPath)
                    .apply(
                        RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .submit(1024, 768)
                    .get()
                setImageViewBitmap(R.id.img_favorite_widget, imageBitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            setTextViewText(R.id.tv_favorite_title_widget, favoriteList[position].title)

            val intent = Intent().apply {
                Bundle().also { extras ->
                    extras.putInt(EXTRA_ITEM, position)
                    putExtras(extras)
                }
            }

            setOnClickFillInIntent(R.id.tv_favorite_title_widget, intent)
        }
    }

    override fun getCount(): Int = favoriteList.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() = favoriteList.clear()
}