package c.m.dicodingmadefavorite.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.m.dicodingmadefavorite.database.ApplicationDatabase
import c.m.dicodingmadefavorite.database.favorite.FavoriteEntity
import c.m.dicodingmadefavorite.database.favorite.asDomainModel
import c.m.dicodingmadefavorite.domain.ContentResult


class FavoriteRepository(applicationDatabase: ApplicationDatabase) {
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
}