package c.dicodingmade.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowData(
    var tvShowTitle: String = "",
    var tvShowReleaseDate: String = "",
    var tvShowPoster: Int = 0,
    var tvShowDescription: String = ""
) : Parcelable
