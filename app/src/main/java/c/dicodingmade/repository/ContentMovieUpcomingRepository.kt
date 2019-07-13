package c.dicodingmade.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentmovieupcoming.ContentMovieUpcomingEntity
import c.dicodingmade.database.contentmovieupcoming.ContentUpcomingByDateEntity
import c.dicodingmade.network.Services
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentMovieUpcomingRepository(applicationDatabase: ApplicationDatabase) {
    private val contentMovieUpcomingDao = applicationDatabase.contentMovieUpcomingDao()
    val contentUpcomingByDate: LiveData<List<ContentUpcomingByDateEntity>> =
        Transformations.map(contentMovieUpcomingDao.getAllDataUpcoming()) {
            it
        }

    suspend fun getMovieUpcomingByDate(today: String): ContentMovieUpcomingEntity {
        return withContext(Dispatchers.IO) {
            contentMovieUpcomingDao.getSingleContentMovieUpcoming(today)
        }
    }

    suspend fun refreshMovieUpcoming() {
        withContext(Dispatchers.IO) {
            val contentList = Services().getMovieUpcoming()
            contentMovieUpcomingDao.updateData(*contentList.asDatabaseModel())
        }
    }

    suspend fun update(contentUpcomingByDateEntity: ArrayList<ContentUpcomingByDateEntity>) {
        withContext(Dispatchers.IO) {
            contentMovieUpcomingDao.updateDataContentUpcoming(contentUpcomingByDateEntity)
        }
    }
}