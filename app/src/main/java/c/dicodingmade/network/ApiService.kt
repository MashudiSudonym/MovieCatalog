package c.dicodingmade.network

import c.dicodingmade.model.MovieData
import c.dicodingmade.model.TvShowData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): MovieData

    @GET("discover/tv")
    suspend fun getTvShowList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): TvShowData
}