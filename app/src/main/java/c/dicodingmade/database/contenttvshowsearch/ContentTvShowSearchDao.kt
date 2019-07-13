package c.dicodingmade.database.contenttvshowsearch

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContentTvShowSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg contentTvShowSearch: ContentTvShowSearchEntity)

    @Query("SELECT * FROM content_tv_show_search_table ORDER BY _id ASC")
    fun getAllContentTvShowSearch(): LiveData<List<ContentTvShowSearchEntity>>

    @Query("DELETE FROM content_tv_show_search_table")
    suspend fun deleteAllContentTvShowSearch()

    @Transaction
    suspend fun updateData(vararg contentTvShowSearch: ContentTvShowSearchEntity) {
        deleteAllContentTvShowSearch()
        insert(*contentTvShowSearch)
    }
}