package c.dicodingmade.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val idFavoriteTable: Long = 0L,
    @ColumnInfo(name = "isMovie")
    var isMovie: Boolean = false,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String = "",
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "overview")
    var overview: String = "",
    @ColumnInfo(name = "poster_path")
    var posterPath: String = "",
    @ColumnInfo(name = "release_date")
    var releaseDate: String = "",
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double = 0.0
)