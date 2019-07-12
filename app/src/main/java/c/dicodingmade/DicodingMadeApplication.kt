package c.dicodingmade

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import c.dicodingmade.di.Module
import c.dicodingmade.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class DicodingMadeApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(12, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()

        startKoin {
            androidLogger()
            androidContext(this@DicodingMadeApplication)
            modules(Module().appModule)
        }
    }
}