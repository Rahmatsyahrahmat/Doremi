package rahmatsyah.doremi.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rahmatsyah.doremi.app.di.appModule
import rahmatsyah.doremi.app.di.appViewModelModul
import rahmatsyah.doremi.data.di.databaseModule
import rahmatsyah.doremi.data.di.networkModule
import rahmatsyah.doremi.data.di.repositoryModule


class DoremiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@DoremiApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    appModule,
                    appViewModelModul
                )
            )
        }
    }
}