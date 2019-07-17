package c.dicodingmade.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.repository.ContentMovieRepository
import c.dicodingmade.repository.ContentMovieUpcomingRepository
import c.dicodingmade.repository.ContentTvShowRepository

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val applicationDatabase = ApplicationDatabase.getDatabase(applicationContext)
        val contentMovieRepository = ContentMovieRepository(applicationDatabase)
        val contentTvShowRepository = ContentTvShowRepository(applicationDatabase)
        val contentMovieUpcomingRepository = ContentMovieUpcomingRepository(applicationDatabase)

        return try {
            contentMovieRepository.refreshContentMovie()
            contentTvShowRepository.refreshContentTvShow()
            contentMovieUpcomingRepository.refreshMovieUpcoming()

            Result.success()
        } catch (e: Throwable) {
            Result.retry()
        }
    }
}