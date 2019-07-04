package c.dicodingmade.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import c.dicodingmade.database.Favorite
import c.dicodingmade.database.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val allFavorite: LiveData<List<Favorite>> = favoriteDao.getAllFavorite()

    @WorkerThread
    suspend fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }
}