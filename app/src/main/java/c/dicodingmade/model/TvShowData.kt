package c.dicodingmade.model

import com.google.gson.annotations.SerializedName


data class TvShowData(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<TvShowResult?>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)