package c.dicodingmade.network

import c.dicodingmade.database.contentmovie.ContentMovieEntity
import c.dicodingmade.database.contentmoviesearch.ContentMovieSearchEntity
import c.dicodingmade.database.contentmovieupcoming.ContentMovieUpcomingEntity
import c.dicodingmade.database.contenttvshow.ContentTvShowEntity
import c.dicodingmade.database.contenttvshowsearch.ContentTvShowSearchEntity
import com.google.gson.annotations.SerializedName

data class NetworkContentDataMovie(
    @SerializedName("results")
    val results: List<NetworkContentResult> = listOf()
)

data class NetworkContentDataTvShow(
    @SerializedName("results")
    val results: List<NetworkContentResult> = listOf()
)

data class NetworkContentDataMovieUpcoming(
    @SerializedName("results")
    val results: List<NetworkContentResult> = listOf()
)

data class NetworkContentDataMovieSearch(
    @SerializedName("results")
    val results: List<NetworkContentResult> = listOf()
)

data class NetworkContentDataTvShowSearch(
    @SerializedName("results")
    val results: List<NetworkContentResult> = listOf()
)

data class NetworkContentResult(
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("popularity")
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("release_date", alternate = ["first_air_date"])
    val releaseDate: String = "",
    @SerializedName("title", alternate = ["name"])
    val title: String = "",
    @SerializedName("video")
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0
)

fun NetworkContentDataTvShow.asDatabaseModel(): Array<ContentTvShowEntity> {
    return results.map {
        ContentTvShowEntity(
            backdropPath = it.backdropPath,
            id = it.id,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            adult = it.adult,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            popularity = it.popularity,
            video = it.video
        )
    }.toTypedArray()
}

fun NetworkContentDataMovie.asDatabaseModel(): Array<ContentMovieEntity> {
    return results.map {
        ContentMovieEntity(
            backdropPath = it.backdropPath,
            id = it.id,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            adult = it.adult,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            popularity = it.popularity,
            video = it.video
        )
    }.toTypedArray()
}

fun NetworkContentDataMovieUpcoming.asDatabaseModel(): Array<ContentMovieUpcomingEntity> {
    return results.map {
        ContentMovieUpcomingEntity(
            backdropPath = it.backdropPath,
            id = it.id,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            adult = it.adult,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            popularity = it.popularity,
            video = it.video
        )
    }.toTypedArray()
}

fun NetworkContentDataMovieSearch.asDatabaseModel(): Array<ContentMovieSearchEntity> {
    return results.map {
        ContentMovieSearchEntity(
            backdropPath = it.backdropPath,
            id = it.id,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            adult = it.adult,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            popularity = it.popularity,
            video = it.video
        )
    }.toTypedArray()
}

fun NetworkContentDataTvShowSearch.asDatabaseModel(): Array<ContentTvShowSearchEntity> {
    return results.map {
        ContentTvShowSearchEntity(
            backdropPath = it.backdropPath,
            id = it.id,
            overview = it.overview,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            adult = it.adult,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            popularity = it.popularity,
            video = it.video
        )
    }.toTypedArray()
}

