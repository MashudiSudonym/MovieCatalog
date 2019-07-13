package c.dicodingmade.database.contentmoviesearch

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContentMovieSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg contentMovieSearch: ContentMovieSearchEntity)

    @Query("SELECT * FROM content_movie_search_table ORDER BY _id ASC")
    fun getAllContentMovieSearch(): LiveData<List<ContentMovieSearchEntity>>

    @Query("DELETE FROM content_movie_search_table")
    suspend fun deleteAllContentMovieSearch()

    @Transaction
    suspend fun updateData(vararg contentMovieSearch: ContentMovieSearchEntity) {
        deleteAllContentMovieSearch()
        insert(*contentMovieSearch)
    }
}