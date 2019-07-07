package c.dicodingmade.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import c.dicodingmade.database.ApplicationDatabase
import c.dicodingmade.repository.ContentMovieRepository
import c.dicodingmade.repository.ContentTvShowRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val applicationDatabase = ApplicationDatabase.getDatabase(applicationContext)
        val contentMovieRepository = ContentMovieRepository(applicationDatabase)
        val contentTvShowRepository = ContentTvShowRepository(applicationDatabase)

        return try {
            contentMovieRepository.refreshContentMovie()
            contentTvShowRepository.refreshContentTvShow()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}