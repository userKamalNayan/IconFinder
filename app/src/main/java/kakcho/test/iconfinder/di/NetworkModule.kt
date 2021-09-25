package kakcho.test.iconfinder.di

import kakcho.test.core.BuildConfig
import kakcho.test.core.data.api.IconFinderService
import kakcho.test.core.data.network.createWebService
import kakcho.test.iconfinder.util.DownloadUtil
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


/**
 * Created by Kamal Nayan on 22-09-2021 at 11:23
 */

val networkModule = module {
    single { createWebService<IconFinderService>(BuildConfig.BASE_URL) }

    single { DownloadUtil(androidApplication().baseContext) }
}