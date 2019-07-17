package c.dicodingmade.database.favorite

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import c.dicodingmade.domain.ContentResult

/*
* This entity code reference from
* https://github.com/googlesamples/android-architecture-components/tree/master/PersistenceContentProviderSample
*/
object ConstantsFavoriteEntity {
    const val COLUMN_ID = "_id"
    const val COLUMN_IS_MOVIE = "isMovie"
    const val COLUMN_IS_TVSHOW = "isTvShow"
    const val COLUMN_IS_FAVORITE = "isFavorite"
    const val COLUMN_BACKDROP_PATH = "backdrop_path"
    const val COLUMN_ID_CONTENT = "id"
    const val COLUMN_OVERVIEW = "overview"
    const val COLUMN_POSTER_PATH = "poster_path"
    const val COLUMN_RELEASE_DATE = "release_date"
    const val COLUMN_TITLE = "title"
    const val COLUMN_VOTE_AVERAGE = "vote_average"
}

@Entity(tableName = "favorite_table")
data class FavoriteEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_ID)
    var idFavoriteTable: Long = 0L,
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_IS_MOVIE)
    var isMovie: Boolean = false,
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_IS_TVSHOW)
    var isTvShow: Boolean = false,
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_IS_FAVORITE)
    var isFavorite: Boolean = false,
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_BACKDROP_PATH)
    var backdropPath: String? = "",
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_ID_CONTENT)
    var id: Int = 0,
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_OVERVIEW)
    var overview: String? = "",
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_POSTER_PATH)
    var posterPath: String? = "",
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_RELEASE_DATE)
    var releaseDate: String? = "",
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_TITLE)
    var title: String? = "",
    @ColumnInfo(name = ConstantsFavoriteEntity.COLUMN_VOTE_AVERAGE)
    var voteAverage: Double = 0.0
) {
    fun fromContentValues(values: ContentValues): FavoriteEntity = FavoriteEntity().apply {
        with(values) {
            if (containsKey(ConstantsFavoriteEntity.COLUMN_ID)) idFavoriteTable =
                getAsLong(ConstantsFavoriteEntity.COLUMN_ID)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_IS_MOVIE)) isMovie =
                getAsBoolean(ConstantsFavoriteEntity.COLUMN_IS_MOVIE)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_IS_TVSHOW)) isTvShow =
                getAsBoolean(ConstantsFavoriteEntity.COLUMN_IS_TVSHOW)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_IS_FAVORITE)) isFavorite =
                getAsBoolean(ConstantsFavoriteEntity.COLUMN_IS_FAVORITE)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_BACKDROP_PATH)) backdropPath =
                getAsString(ConstantsFavoriteEntity.COLUMN_BACKDROP_PATH)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_ID_CONTENT)) id =
                getAsInteger(ConstantsFavoriteEntity.COLUMN_ID_CONTENT)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_OVERVIEW)) overview =
                getAsString(ConstantsFavoriteEntity.COLUMN_OVERVIEW)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_POSTER_PATH)) posterPath =
                getAsString(ConstantsFavoriteEntity.COLUMN_POSTER_PATH)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_RELEASE_DATE)) releaseDate =
                getAsString(ConstantsFavoriteEntity.COLUMN_RELEASE_DATE)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_TITLE)) title =
                getAsString(ConstantsFavoriteEntity.COLUMN_TITLE)
            if (containsKey(ConstantsFavoriteEntity.COLUMN_VOTE_AVERAGE)) voteAverage =
                getAsDouble(ConstantsFavoriteEntity.COLUMN_VOTE_AVERAGE)
        }
    }
}

fun List<FavoriteEntity>.asDomainModel(): List<ContentResult> {
    return map {
        ContentResult(
            isMovie = it.isMovie,
            isFavorite = it.isFavorite,
            isTvShow = it.isTvShow,
            backdropPath = it.backdropPath.toString(),
            id = it.id,
            overview = it.overview.toString(),
            posterPath = it.posterPath.toString(),
            releaseDate = it.releaseDate.toString(),
            title = it.title.toString(),
            voteAverage = it.voteAverage
        )
    }
}