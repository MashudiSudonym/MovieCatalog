package c.dicodingmade.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import c.dicodingmade.database.ApplicationDatabase

class DicodingMadeContentProvider : ContentProvider() {
    private lateinit var applicationDatabase: ApplicationDatabase

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? = throw IllegalArgumentException("BLOCK")

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = applicationDatabase.favoriteDao().readFavoriteCursor().apply {
        setNotificationUri(context?.applicationContext?.contentResolver, uri)
    }

    override fun onCreate(): Boolean {
        applicationDatabase = ApplicationDatabase.getDatabase(context as Context)
        return true
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int =
        throw IllegalArgumentException("BLOCK")

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int =
        throw IllegalArgumentException("BLOCK")

    override fun getType(uri: Uri): String? = null
}