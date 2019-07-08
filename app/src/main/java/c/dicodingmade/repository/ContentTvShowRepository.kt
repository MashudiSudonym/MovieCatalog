package c.dicodingmade.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentTvShow.asDomainModel
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.network.Services
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentTvShowRepository(applicationDatabase: ApplicationDatabase) {
    private val contentTvShowDao = applicationDatabase.contentTvShowDao()
    val contentTvShow: LiveData<List<ContentResult>> = Transformations.map(contentTvShowDao.getAllContentTvShow()) {
        it.asDomainModel()
    }

    suspend fun refreshContentTvShow() {
        withContext(Dispatchers.IO) {
            val contentList = Services().getTvShows()
            contentTvShowDao.updateData(*contentList.asDatabaseModel())
        }
    }
}