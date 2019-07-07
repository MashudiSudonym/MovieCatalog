package c.dicodingmade.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): NetworkContentDataMovie

    @GET("discover/tv")
    suspend fun getTvShowList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): NetworkContentDataTvShow

    @GET("search/movie")
    suspend fun getSearchMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): NetworkContentDataMovie

    @GET("search/tv")
    suspend fun getSearchTvShowList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): NetworkContentDataTvShow
}