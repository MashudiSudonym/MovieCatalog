package c.dicodingmade.database.contentmovieupcoming

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContentMovieUpcomingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg contentMovieUpcoming: ContentMovieUpcomingEntity)

    @Query("SELECT * FROM content_movie_upcoming_table ORDER BY _id ASC")
    fun getAllContentMovieUpcoming(): LiveData<List<ContentMovieUpcomingEntity>>

    @Query("SELECT * FROM content_movie_upcoming_table WHERE release_date = :today")
    fun getContentMovieUpcoming(today: String): List<ContentMovieUpcomingEntity>

    @Query("DELETE FROM content_movie_upcoming_table")
    suspend fun deleteAllContentMovie()

    @Transaction
    suspend fun updateData(vararg contentMovieUpcoming: ContentMovieUpcomingEntity) {
        deleteAllContentMovie()
        insert(*contentMovieUpcoming)
    }
}