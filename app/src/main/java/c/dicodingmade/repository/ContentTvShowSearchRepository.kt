package c.dicodingmade.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contenttvshowsearch.asDomainModel
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.network.Services
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentTvShowSearchRepository(applicationDatabase: ApplicationDatabase) {
    private val contentTvShowSearchDao = applicationDatabase.contentTvShowSearchDao()
    val contentTvShowSearch: LiveData<List<ContentResult>> =
        Transformations.map(contentTvShowSearchDao.getAllContentTvShowSearch()) {
            it.asDomainModel()
        }

    suspend fun getTvShowSearch(query: String) {
        withContext(Dispatchers.IO) {
            val contentList = Services().getSearchTvShows(query)
            contentTvShowSearchDao.updateData(*contentList.asDatabaseModel())
        }
    }
}