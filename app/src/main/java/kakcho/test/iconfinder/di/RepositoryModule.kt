package kakcho.test.iconfinder.di

import kakcho.test.core.data.repository.IconsOfSetRepositoryImpl
import kakcho.test.core.domain.repository.IconsOfSetRepository
import kakcho.test.core.domain.usecase.GetIconsOfSetUseCase
import org.koin.dsl.module

/**
 * Created by Kamal Nayan on 22-09-2021 at 11:54
 */

val repositoryModule = module {
    single<IconsOfSetRepository> { IconsOfSetRepositoryImpl(iconFinderService = get()) }

    factory {
        GetIconsOfSetUseCase(iconsOfSetRepository = get())
    }
}