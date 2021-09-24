package kakcho.test.core.domain.usecase

import kakcho.test.core.domain.repository.IconRepository

/**
 * Created by Kamal Nayan on 22-09-2021 at 23:22
 */
class SearchIconUseCase(private val iconRepository: IconRepository) {
    suspend fun invoke(query: String) = iconRepository.searchIcon(query)
}