package kakcho.test.iconfinder.di

import kakcho.test.iconfinder.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Kamal Nayan on 22-09-2021 at 14:10
 */
val viewModelModule = module {
    viewModel { MainViewModel(getIconsOfSetUseCase = get()) }
}