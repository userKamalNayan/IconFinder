package kakcho.test.iconfinder.di

import kakcho.test.iconfinder.MainViewModel
import kakcho.test.iconfinder.ui.category.CategoryViewModel
import kakcho.test.iconfinder.ui.icons.IconsViewModel
import kakcho.test.iconfinder.ui.selectedcategory.SelectedCategoryIconSetsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Kamal Nayan on 22-09-2021 at 14:10
 */
val viewModelModule = module {
    viewModel {
        MainViewModel(
            getIconsOfSetUseCase = get(),
            searchIconUseCase = get()
        )
    }

    viewModel {
        CategoryViewModel(getAllCategoriesUseCase = get())
    }

    viewModel {
        SelectedCategoryIconSetsViewModel(getIconSetOfSelectedCategoryUseCase = get())
    }

    viewModel {
        IconsViewModel(getIconsOfSetUseCase = get())
    }

}