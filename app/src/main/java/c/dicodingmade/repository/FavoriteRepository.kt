package c.dicodingmade.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.favorite.FavoriteEntity
import c.dicodingmade.database.favorite.asDomainModel
import c.dicodingmade.domain.ContentResult


class FavoriteRepository(private val applicationDatabase: ApplicationDatabase) {
    fun getAllMovieFavorite(): LiveData<List<ContentResult>> =
        Transformations.map(applicationDatabase.favoriteDao().getAllFavoriteMovie()) {
            it.asDomainModel()
        }

    fun getAllTvShowFavorite(): LiveData<List<ContentResult>> =
        Transformations.map(applicationDatabase.favoriteDao().getAllFavoriteTvShow()) {
            it.asDomainModel()
        }

    fun getFavoriteById(id: Int): FavoriteEntity? = applicationDatabase.favoriteDao().getFavoriteById(id)

    @WorkerThread
    suspend fun deleteFavoriteById(id: Int) = applicationDatabase.favoriteDao().deleteFavoriteById(id)

    @WorkerThread
    suspend fun insert(favorite: FavoriteEntity) = applicationDatabase.favoriteDao().insert(favorite)
}