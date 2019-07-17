package c.m.dicodingmadefavorite.database.favorite

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(favorite: MutableList<FavoriteEntity>)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFavorite()

    @Transaction
    suspend fun updateData(favorite: MutableList<FavoriteEntity>) {
        deleteAllFavorite()
        insertData(favorite)
    }

    @Query("SELECT * FROM favorite_table ORDER BY _id DESC")
    fun readFavorite(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_table WHERE isMovie = 1 ORDER BY _id DESC")
    fun getAllFavoriteMovie(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE isTvShow = 1 ORDER BY _id DESC")
    fun getAllFavoriteTvShow(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    fun getFavoriteById(id: Int): FavoriteEntity?

    @Query("DELETE FROM favorite_table WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)
}