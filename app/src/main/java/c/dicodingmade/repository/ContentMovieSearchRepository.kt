package c.dicodingmade.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentmoviesearch.asDomainModel
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.network.Services
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentMovieSearchRepository(applicationDatabase: ApplicationDatabase) {
    private val contentMovieSearchDao = applicationDatabase.contentMovieSearchDao()
    val contentMovieSearch: LiveData<List<ContentResult>> =
        Transformations.map(contentMovieSearchDao.getAllContentMovieSearch()) {
            it.asDomainModel()
        }

    suspend fun getMovieSearch(query: String) {
        withContext(Dispatchers.IO) {
            val contentList = Services().getSearchMovies(query)
            contentMovieSearchDao.updateData(*contentList.asDatabaseModel())
        }
    }
}