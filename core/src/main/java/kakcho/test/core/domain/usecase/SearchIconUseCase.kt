package kakcho.test.core.domain.usecase

import kakcho.test.core.domain.repository.IconsOfSetRepository

/**
 * Created by Kamal Nayan on 22-09-2021 at 23:22
 */
class SearchIconUseCase(private val iconFinderRepository: IconsOfSetRepository) {
    suspend fun invoke(query: String) = iconFinderRepository.searchIcon(query)
}