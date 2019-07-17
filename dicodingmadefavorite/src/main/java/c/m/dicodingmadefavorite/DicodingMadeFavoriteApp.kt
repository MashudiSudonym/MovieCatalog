package c.m.dicodingmadefavorite

import android.app.Application
import c.m.dicodingmadefavorite.di.Module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DicodingMadeFavoriteApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DicodingMadeFavoriteApp)
            modules(Module().appModule)
        }
    }
}