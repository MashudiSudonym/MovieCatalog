package c.dicodingmade.repository

import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentmovieupcoming.ContentMovieUpcomingEntity
import c.dicodingmade.network.Services
import c.dicodingmade.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentMovieUpcomingRepository(applicationDatabase: ApplicationDatabase) {
    private val contentMovieUpcomingDao = applicationDatabase.contentMovieUpcomingDao()

    suspend fun getMovieUpcomingByDate(today: String): List<ContentMovieUpcomingEntity> {
        return withContext(Dispatchers.IO) {
            contentMovieUpcomingDao.getContentMovieUpcoming(today)
        }
    }

    suspend fun refreshMovieUpcoming() {
        withContext(Dispatchers.IO) {
            val contentList = Services().getMovieUpcoming()
            contentMovieUpcomingDao.updateData(*contentList.asDatabaseModel())
        }
    }
}