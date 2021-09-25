package kakcho.test.core.domain.usecase

import kakcho.test.core.domain.repository.IconRepository

/**
 * Created by Kamal Nayan on 22-09-2021 at 11:17
 */
class GetIconsOfSetUseCase(private val iconRepository: IconRepository) {
    suspend fun invoke(setID: String, count: Int, offset: Int) =
        iconRepository.getIconsOfSet(setID, count, offset)
}