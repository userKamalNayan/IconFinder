package kakcho.test.core.domain.usecase

import kakcho.test.core.domain.repository.IconRepository

/**
 * Created by Kamal Nayan on 25-09-2021 at 02:38
 */
class GetIconSetOfSelectedCategoryUseCase(private val iconRepository: IconRepository) {
    suspend fun invoke(identifier: String, count: Int, after: String) =
        iconRepository.getIconSetOfCategory(identifier, count, after);
}