package c.dicodingmade.database.contentMovie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContentMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg contentMovie: ContentMovieEntity)

    @Query("SELECT * FROM content_movie_table ORDER BY _id ASC")
    fun getAllContentMovie(): LiveData<List<ContentMovieEntity>>

    @Query("DELETE FROM content_movie_table")
    suspend fun deleteAllContentMovie()
}