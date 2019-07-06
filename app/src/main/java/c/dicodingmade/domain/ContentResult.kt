package c.dicodingmade.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContentResult(
    var isMovie: Boolean = false,
    var isTvShow: Boolean = false,
    var isFavorite: Boolean = false,
    val adult: Boolean = false,
    val backdropPath: String = "",
    val id: Int = 0,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0
) : Parcelable