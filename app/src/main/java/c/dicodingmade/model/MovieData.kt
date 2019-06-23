package c.dicodingmade.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieData(
    var movieTitle: String = "",
    var movieReleaseDate: String = "",
    var moviePoster: Int = 0,
    var movieDescription: String = ""
) : Parcelable
