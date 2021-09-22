package kakcho.test.core.data.repository

import kakcho.test.core.BuildConfig
import kakcho.test.core.data.api.IconFinderService
import kakcho.test.core.domain.repository.IconsOfSetRepository
import timber.log.Timber

/**
 * Created by Kamal Nayan on 22-09-2021 at 11:04
 */
class IconsOfSetRepositoryImpl(private val iconFinderService: IconFinderService) :
    IconsOfSetRepository {
    override suspend fun getIconsOfSet() {
        Timber.i("result will come")
        val result = iconFinderService.getIcons(
            setId = "1761",
            api = "Bearer "+BuildConfig.API_KEY
        )
        if (result.isSuccessful ){
            result.body()
        }
        Timber.i("result =  ${result.isSuccessful} ${result.raw().toString()} \n message =  ${result.message()} and result =\n ${result.body()}")
    }
}