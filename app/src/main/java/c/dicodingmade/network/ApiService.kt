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
        @Query("query") query: String? = null
    ): NetworkContentDataMovieSearch

    @GET("search/tv")
    suspend fun getSearchTvShowList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String? = null
    ): NetworkContentDataTvShowSearch

    @GET("movie/upcoming")
    suspend fun getMovieUpcoming(
        @Query("api_key") apiKey: String
    ): NetworkContentDataMovieUpcoming
}