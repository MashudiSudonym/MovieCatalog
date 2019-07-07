package c.dicodingmade.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.BuildConfig
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentTvShow.asDomainModel
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.network.ApiService
import c.dicodingmade.network.RetrofitBuilder
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentTvShowRepository(private val applicationDatabase: ApplicationDatabase) {
    val contentTvShow: LiveData<List<ContentResult>> =
        Transformations.map(applicationDatabase.contentTvShowDao().getAllContentTvShow()) {
            it.asDomainModel()
        }

    suspend fun refreshContentTvShow() {
        withContext(Dispatchers.IO) {
            val services = RetrofitBuilder.getInstance(BuildConfig.BASE_URL_API)
                .create(ApiService::class.java)
            val contentList = services.getTvShowList(BuildConfig.TOKEN, "en-US")
            applicationDatabase.contentTvShowDao().updateData(*contentList.asDatabaseModel())
        }
    }
}