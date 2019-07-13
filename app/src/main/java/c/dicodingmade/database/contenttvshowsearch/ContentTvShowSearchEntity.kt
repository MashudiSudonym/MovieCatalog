package c.dicodingmade.database.contenttvshowsearch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import c.dicodingmade.domain.ContentResult

@Entity(tableName = "content_tv_show_search_table")
data class ContentTvShowSearchEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val idContentTable: Long = 0L,
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String? = "",
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "overview")
    var overview: String? = "",
    @ColumnInfo(name = "poster_path")
    var posterPath: String? = "",
    @ColumnInfo(name = "release_date")
    var releaseDate: String? = "",
    @ColumnInfo(name = "title")
    var title: String? = "",
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double = 0.0,
    @ColumnInfo(name = "vote_count")
    var voteCount: Int = 0,
    @ColumnInfo(name = "adult")
    var adult: Boolean = false,
    @ColumnInfo(name = "original_language")
    var originalLanguage: String? = "",
    @ColumnInfo(name = "original_title")
    var originalTitle: String? = "",
    @ColumnInfo(name = "popularity")
    var popularity: Double = 0.0,
    @ColumnInfo(name = "video")
    var video: Boolean = false
)

fun List<ContentTvShowSearchEntity>.asDomainModel(): List<ContentResult> {
    return map {
        ContentResult(
            backdropPath = it.backdropPath.toString(),
            id = it.id,
            overview = it.overview.toString(),
            posterPath = it.posterPath.toString(),
            releaseDate = it.releaseDate.toString(),
            title = it.title.toString(),
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            adult = it.adult,
            originalLanguage = it.originalLanguage.toString(),
            originalTitle = it.originalTitle.toString(),
            popularity = it.popularity,
            video = it.video
        )
    }
}