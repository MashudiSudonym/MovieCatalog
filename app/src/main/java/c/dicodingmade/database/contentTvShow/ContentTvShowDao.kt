package c.dicodingmade.database.contentTvShow

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContentTvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg contentTvShow: ContentTvShowEntity)

    @Query("SELECT * FROM content_tv_show_table ORDER BY _id ASC")
    fun getAllContentTvShow(): LiveData<List<ContentTvShowEntity>>

    @Query("DELETE FROM content_tv_show_table")
    suspend fun deleteAllContentTvShow()
}