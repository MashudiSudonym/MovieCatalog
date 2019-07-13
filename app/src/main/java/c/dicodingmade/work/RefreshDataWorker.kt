package c.dicodingmade.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.database.contentmovieupcoming.ContentMovieUpcomingEntity
import c.dicodingmade.database.contentmovieupcoming.ContentUpcomingByDateEntity
import c.dicodingmade.repository.ContentMovieRepository
import c.dicodingmade.repository.ContentMovieUpcomingRepository
import c.dicodingmade.repository.ContentTvShowRepository
import java.text.SimpleDateFormat
import java.util.*

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val applicationDatabase = ApplicationDatabase.getDatabase(applicationContext)
        val contentMovieRepository = ContentMovieRepository(applicationDatabase)
        val contentTvShowRepository = ContentTvShowRepository(applicationDatabase)
        val contentMovieUpcomingRepository = ContentMovieUpcomingRepository(applicationDatabase)
        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(date)
        val upcomingMovies: ArrayList<ContentUpcomingByDateEntity> = arrayListOf()

        return try {
            contentMovieRepository.refreshContentMovie()
            contentTvShowRepository.refreshContentTvShow()
            contentMovieUpcomingRepository.refreshMovieUpcoming()

            val upcomingByDate = contentMovieUpcomingRepository.getMovieUpcomingByDate(currentDate)
            val listUpcomingMovie: ArrayList<ContentMovieUpcomingEntity> = arrayListOf()
            listUpcomingMovie.clear()
            listUpcomingMovie.addAll(listOf(upcomingByDate))

            if (listUpcomingMovie.firstOrNull() != null) {
                for (i in listUpcomingMovie.indices) {
                    val addUpcomingMovieByDate = ContentUpcomingByDateEntity().apply {
                        id = listUpcomingMovie[i].id
                        title = listUpcomingMovie[i].title
                        overview = listUpcomingMovie[i].overview
                    }
                    upcomingMovies.addAll(listOf(addUpcomingMovieByDate))
                }
                contentMovieUpcomingRepository.update(upcomingMovies)
            }

            Result.success()
        } catch (e: Throwable) {
            Result.retry()
        }
    }
}