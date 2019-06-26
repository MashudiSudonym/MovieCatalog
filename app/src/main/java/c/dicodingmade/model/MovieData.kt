package c.dicodingmade.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieData(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<MovieResult> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
) : Parcelable