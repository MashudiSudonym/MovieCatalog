package c.dicodingmade.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieTvShowData(
    var movieTvShowTitle: String = "",
    var movieTvShowReleaseDate: String = "",
    var movieTvShowPoster: Int = 0,
    var movieTvShowDescription: String = ""
) : Parcelable
