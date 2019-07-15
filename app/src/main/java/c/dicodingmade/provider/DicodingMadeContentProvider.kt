package c.dicodingmade.provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.favorite.FavoriteEntity

/*
* This code reference from
* https://github.com/googlesamples/android-architecture-components/tree/master/PersistenceContentProviderSample
*/

class DicodingMadeContentProvider : ContentProvider() {
    companion object {
        const val AUTHORITY = "c.dicodingmade.provider"
        const val FAVORITE_DIR = 1
        const val FAVORITE_ITEM = 2
        const val FAVORITE_TABLE = "favorite_table"
    }

    private lateinit var applicationDatabase: ApplicationDatabase
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(AUTHORITY, FAVORITE_TABLE, FAVORITE_DIR)
        uriMatcher.addURI(AUTHORITY, "$FAVORITE_TABLE/#", FAVORITE_ITEM)
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = applicationDatabase.favoriteDao().readFavoriteCursor().apply {
        setNotificationUri(context?.applicationContext?.contentResolver, uri)
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            FAVORITE_DIR -> {
                if (context == null) return null

                val id = applicationDatabase.favoriteDao()
                    .insertContentProvider(FavoriteEntity().fromContentValues(contentValues as ContentValues))
                context?.contentResolver?.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            FAVORITE_ITEM -> throw  IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
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

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            FAVORITE_DIR -> throw  IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            FAVORITE_ITEM -> {
                if (context == null) return 0
                val id = applicationDatabase.favoriteDao().deleteContentProviderById(ContentUris.parseId(uri))
                context?.contentResolver?.notifyChange(uri, null)
                return id
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? = null
}