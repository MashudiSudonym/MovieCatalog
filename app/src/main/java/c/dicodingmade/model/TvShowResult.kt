package c.dicodingmade.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowResult(
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("first_air_date")
    val firstAirDate: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_name")
    val originalName: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("popularity")
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0
) : Parcelable