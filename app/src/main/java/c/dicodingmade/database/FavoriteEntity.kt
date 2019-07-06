package c.dicodingmade.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import c.dicodingmade.model.ContentResult

@Entity(tableName = "favorite_table")
data class FavoriteEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val idFavoriteTable: Long = 0L,
    @ColumnInfo(name = "isMovie")
    var isMovie: Boolean = false,
    @ColumnInfo(name = "isTvShow")
    var isTvShow: Boolean = false,
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

fun List<FavoriteEntity>.asDomainModel(): List<ContentResult> {
    return map {
        ContentResult(
            isMovie = it.isMovie,
            isFavorite = it.isFavorite,
            isTvShow = it.isTvShow,
            backdropPath = it.backdropPath,
            id = it.id,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage
        )
    }
}