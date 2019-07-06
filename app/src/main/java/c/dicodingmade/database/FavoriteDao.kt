package c.dicodingmade.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite_table ORDER BY _id DESC")
    fun getAllFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE isMovie = 1 ORDER BY _id DESC")
    fun getAllFavoriteMovie(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE isTvShow = 1 ORDER BY _id DESC")
    fun getAllFavoriteTvShow(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    fun getFavoriteById(id: Int): FavoriteEntity?

    @Query("DELETE FROM favorite_table WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAll()
}