package c.m.dicodingmadefavorite.repository

import android.app.Application
import android.net.Uri
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.m.dicodingmadefavorite.database.ApplicationDatabase
import c.m.dicodingmadefavorite.database.favorite.FavoriteEntity
import c.m.dicodingmadefavorite.database.favorite.asDomainModel
import c.m.dicodingmadefavorite.domain.ContentResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FavoriteRepository(applicationDatabase: ApplicationDatabase) {
    companion object {
        const val URI = "content://c.dicodingmade.provider/favorite_table"
        const val COLUMN_IS_MOVIE = "isMovie"
        const val COLUMN_IS_TVSHOW = "isTvShow"
        const val COLUMN_IS_FAVORITE = "isFavorite"
        const val COLUMN_BACKDROP_PATH = "backdrop_path"
        const val COLUMN_ID_CONTENT = "id"
        const val COLUMN_OVERVIEW = "overview"
        const val COLUMN_POSTER_PATH = "poster_path"
        const val COLUMN_RELEASE_DATE = "release_date"
        const val COLUMN_TITLE = "title"
        const val COLUMN_VOTE_AVERAGE = "vote_average"
    }
    private val favoriteDao = applicationDatabase.favoriteDao()

    fun getAllMovieFavorite(): LiveData<List<ContentResult>> =
        Transformations.map(favoriteDao.getAllFavoriteMovie()) {
            it.asDomainModel()
        }

    fun getAllTvShowFavorite(): LiveData<List<ContentResult>> =
        Transformations.map(favoriteDao.getAllFavoriteTvShow()) {
            it.asDomainModel()
        }

    fun getFavoriteById(id: Int): FavoriteEntity? = favoriteDao.getFavoriteById(id)

    @WorkerThread
    suspend fun deleteFavoriteById(id: Int) = favoriteDao.deleteFavoriteById(id)

    @WorkerThread
    suspend fun insert(favorite: FavoriteEntity) = favoriteDao.insert(favorite)

    suspend fun getContentProvider(application: Application) {
        val cursor = application.contentResolver?.query(
            Uri.parse(URI),
            null,
            null,
            null,
            null
        )
        val cursorData: MutableList<FavoriteEntity> = mutableListOf()

        if (cursor != null && cursor.moveToFirst()) {
            do {
                cursorData.add(
                    FavoriteEntity(
                        isMovie = when (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_MOVIE))) {
                            1 -> true
                            else -> false
                        },
                        isTvShow = when (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_TVSHOW))) {
                            1 -> true
                            else -> false
                        },
                        isFavorite = when (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE))) {
                            1 -> true
                            else -> false
                        },
                        backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BACKDROP_PATH)),
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_CONTENT)),
                        overview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)),
                        posterPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)),
                        releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE_DATE)),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VOTE_AVERAGE))
                    )
                )
            } while (cursor.moveToNext())
            cursor.close()
        }

        withContext(Dispatchers.IO) {
            favoriteDao.updateData(cursorData)
        }
    }
}