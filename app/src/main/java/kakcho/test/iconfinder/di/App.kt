package kakcho.test.iconfinder.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

/**
 * Created by Kamal Nayan on 22-09-2021 at 14:55
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupDebuggingTools()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(appModule)
        }
    }

    private fun setupDebuggingTools() {
        Timber.plant(Timber.DebugTree())
    }
}