package c.dicodingmade.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite_table ORDER BY _id DESC")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    fun getFavoriteById(id: Int): Favorite?

    @Query("DELETE FROM favorite_table WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAll()
}