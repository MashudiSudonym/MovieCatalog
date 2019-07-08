package c.dicodingmade.network

import c.dicodingmade.BuildConfig

class Services {
    companion object {
        const val BASE_URL_API = BuildConfig.BASE_URL_API
        const val TOKEN = BuildConfig.TOKEN
        const val BASE_URL_POSTER = BuildConfig.BASE_URL_POSTER
        const val EN_LANGUAGE = "en-US"
    }

    private val services = RetrofitBuilder.getInstance(BASE_URL_API).create(ApiService::class.java)

    suspend fun getMovies() = services.getMovieList(TOKEN, EN_LANGUAGE)
    suspend fun getTvShows() = services.getTvShowList(TOKEN, EN_LANGUAGE)
}