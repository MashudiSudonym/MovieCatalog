package c.dicodingmade.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import c.dicodingmade.database.contentMovie.ContentMovieDatabase
import c.dicodingmade.database.contentTvShow.ContentTvShowDatabase
import c.dicodingmade.repository.ContentMovieRepository
import c.dicodingmade.repository.ContentTvShowRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val contentMovieDatabase = ContentMovieDatabase.getDatabase(applicationContext)
        val contentTvShowDatabase = ContentTvShowDatabase.getDatabase(applicationContext)
        val contentMovieRepository = ContentMovieRepository(contentMovieDatabase)
        val contentTvShowRepository = ContentTvShowRepository(contentTvShowDatabase)

        return try {
            contentMovieRepository.refreshContentMovie()
            contentTvShowRepository.refreshContentTvShow()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}