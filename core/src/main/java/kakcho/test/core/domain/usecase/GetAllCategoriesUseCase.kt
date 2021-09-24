package kakcho.test.core.domain.usecase

import kakcho.test.core.domain.repository.IconRepository

/**
 * Created by Kamal Nayan on 24-09-2021 at 23:55
 */
class GetAllCategoriesUseCase(private val iconRepository: IconRepository) {
    suspend fun invoke(count: Int, after: String) =
        iconRepository.getALlCategories(count, after);
}