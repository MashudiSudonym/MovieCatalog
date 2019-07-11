package c.dicodingmade.database.contentMovieUpcoming

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContentMovieUpcomingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg contentMovieUpcoming: ContentMovieUpcomingEntity)

    @Query("SELECT * FROM content_movie_upcoming_table ORDER BY _id ASC")
    fun getAllContentMovieUpcoming(): LiveData<List<ContentMovieUpcomingEntity>>

    @Query("SELECT * FROM content_movie_upcoming_table WHERE release_date = :today LIMIT 1")
    fun getSingleContentMovieUpcoming(today: String): ContentMovieUpcomingEntity

    @Query("DELETE FROM content_movie_upcoming_table")
    suspend fun deleteAllContentMovie()

    @Transaction
    suspend fun updateData(vararg contentMovieUpcoming: ContentMovieUpcomingEntity) {
        deleteAllContentMovie()
        insert(*contentMovieUpcoming)
    }
}