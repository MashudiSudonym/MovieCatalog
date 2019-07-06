package c.dicodingmade.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.database.FavoriteDao
import c.dicodingmade.database.FavoriteEntity
import c.dicodingmade.database.asDomainModel
import c.dicodingmade.model.ContentResult


class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getAllMovieFavorite(): LiveData<List<ContentResult>> = Transformations.map(favoriteDao.getAllFavoriteMovie()) {
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
}