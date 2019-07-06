package c.dicodingmade.network

import c.dicodingmade.model.ContentData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): ContentData

    @GET("discover/tv")
    suspend fun getTvShowList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): ContentData
}