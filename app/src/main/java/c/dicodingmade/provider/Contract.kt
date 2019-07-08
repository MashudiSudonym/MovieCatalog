package c.dicodingmade.provider

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

object Contract {
    val AUTHORITY = "c.dicodingmade"
    private val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            val TABLE_NAME = "favorite_table"
            val IS_MOVIE = "isMovie"
            val IS_TV_SHOW = "isTvShow"
            val IS_FAVORITE = "isFavorite"
            val BACKDROP_PATH = "backdrop_path"
            val ID = "id"
            val OVERVIEW = "overview"
            val POSTER_PATH = "poster_path"
            val RELEASE_DATE = "release_date"
            val TITLE = "title"
            val VOTE_AVERAGE = "vote_average"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    fun getColumnString(cursor: Cursor, columnName: String): String =
        cursor.getString(cursor.getColumnIndex(columnName))

    fun getColumnInt(cursor: Cursor, columnName: String): Int =
        cursor.getInt(cursor.getColumnIndex(columnName))

    fun getColumnLong(cursor: Cursor, columnName: String): Long =
        cursor.getLong(cursor.getColumnIndex(columnName))
}