package c.dicodingmade.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.BuildConfig
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentMovie.asDomainModel
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.network.ApiService
import c.dicodingmade.network.RetrofitBuilder
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentMovieRepository(private val applicationDatabase: ApplicationDatabase) {
    val contentMovie: LiveData<List<ContentResult>> =
        Transformations.map(applicationDatabase.contentMovieDao().getAllContentMovie()) {
            it.asDomainModel()
        }

    suspend fun refreshContentMovie() {
        withContext(Dispatchers.IO) {
            val services = RetrofitBuilder.getInstance(BuildConfig.BASE_URL_API)
                .create(ApiService::class.java)
            val contentList = services.getMovieList(BuildConfig.TOKEN, "en-US")
            applicationDatabase.contentMovieDao().updateData(*contentList.asDatabaseModel())
        }
    }
}