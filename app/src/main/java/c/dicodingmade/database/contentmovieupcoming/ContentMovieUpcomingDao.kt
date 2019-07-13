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
    fun getSingleContentMovieUpcoming(today: String): ContentMovieUpcomingEntity

    @Query("DELETE FROM content_movie_upcoming_table")
    suspend fun deleteAllContentMovie()

    @Transaction
    suspend fun updateData(vararg contentMovieUpcoming: ContentMovieUpcomingEntity) {
        deleteAllContentMovie()
        insert(*contentMovieUpcoming)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contentUpcomingByDateEntity: ArrayList<ContentUpcomingByDateEntity>)

    @Query("DELETE FROM content_upcoming_by_date_table")
    suspend fun deleteAllContentUpcoming()

    @Transaction
    suspend fun updateDataContentUpcoming(contentUpcomingByDateEntity: ArrayList<ContentUpcomingByDateEntity>) {
        deleteAllContentUpcoming()
        insert(contentUpcomingByDateEntity)
    }

    @Query("SELECT * FROM content_upcoming_by_date_table ORDER BY _id DESC")
    fun getAllDataUpcoming(): LiveData<List<ContentUpcomingByDateEntity>>
}