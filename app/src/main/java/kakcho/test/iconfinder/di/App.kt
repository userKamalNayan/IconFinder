package kakcho.test.iconfinder.di

import android.app.Application
import kakcho.test.core.exceptionhandling.ReleaseTree
import kakcho.test.iconfinder.BuildConfig
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

        initKoin()
        initDebugTools()
    }

    /**
     * Initializing koin for DI
     */
    private fun initKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(appModule)
        }
    }

    /**
     * Initializing debugging tools
     *
     * Timber is used for logging and in release mode
     * it will directly report to cloud if the priority
     * of log is [Log.ERROR] or [Log.WARN]
     *
     */
    private fun initDebugTools() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(ReleaseTree())
    }
}