package kakcho.test.core.domain.usecase

import kakcho.test.core.domain.repository.IconsOfSetRepository

/**
 * Created by Kamal Nayan on 22-09-2021 at 11:17
 */
class GetIconsOfSetUseCase(private val iconsOfSetRepository: IconsOfSetRepository) {
    suspend fun invoke() = iconsOfSetRepository.getIconsOfSet()
}