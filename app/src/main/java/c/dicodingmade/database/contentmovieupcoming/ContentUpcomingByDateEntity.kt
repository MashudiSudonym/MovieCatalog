package c.dicodingmade.database.contentmovieupcoming

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_upcoming_by_date_table")
data class ContentUpcomingByDateEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val idContentTable: Long = 0L,
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "overview")
    var overview: String = "",
    @ColumnInfo(name = "title")
    var title: String = ""
)