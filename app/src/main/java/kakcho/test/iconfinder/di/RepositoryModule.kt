package kakcho.test.iconfinder.di

import kakcho.test.core.data.repository.IconRepositoryImpl
import kakcho.test.core.domain.repository.IconRepository
import kakcho.test.core.domain.usecase.GetAllCategoriesUseCase
import kakcho.test.core.domain.usecase.GetIconSetOfSelectedCategoryUseCase
import kakcho.test.core.domain.usecase.GetIconsOfSetUseCase
import kakcho.test.core.domain.usecase.SearchIconUseCase
import org.koin.dsl.module

/**
 * Created by Kamal Nayan on 22-09-2021 at 11:54
 */

val repositoryModule = module {
    single<IconRepository> { IconRepositoryImpl(iconFinderService = get()) }

    factory {
        GetIconsOfSetUseCase(iconRepository = get())
    }

    factory {
        SearchIconUseCase(iconRepository = get())
    }

    factory {
        GetAllCategoriesUseCase(iconRepository = get())
    }

    factory {
        GetIconSetOfSelectedCategoryUseCase(iconRepository = get())
    }
}