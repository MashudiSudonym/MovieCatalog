package c.dicodingmade.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentmovie.asDomainModel
import c.dicodingmade.domain.ContentResult
import c.dicodingmade.network.Services
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentMovieRepository(applicationDatabase: ApplicationDatabase) {
    private val contentMovieDao = applicationDatabase.contentMovieDao()
    val contentMovie: LiveData<List<ContentResult>> = Transformations.map(contentMovieDao.getAllContentMovie()) {
            it.asDomainModel()
        }

    suspend fun refreshContentMovie() {
        withContext(Dispatchers.IO) {
            val contentList = Services().getMovies()
            contentMovieDao.updateData(*contentList.asDatabaseModel())
        }
    }
}