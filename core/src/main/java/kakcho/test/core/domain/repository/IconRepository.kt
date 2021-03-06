package kakcho.test.core.domain.repository

import kakcho.test.core.data.model.network.Failure
import kakcho.test.core.data.model.network.Result
import kakcho.test.core.data.model.response.CategoryResponse
import kakcho.test.core.data.model.response.IconSetsResponse
import kakcho.test.core.data.model.response.IconsResponse

/**
 * Created by Kamal Nayan on 22-09-2021 at 10:59
 */
interface IconRepository {

    suspend fun getIconsOfSet(
        setID: String,
        count: Int,
        offset: Int
    ): Result<IconsResponse, Failure>

    suspend fun searchIcon(query: String): Result<IconsResponse, Failure>

    suspend fun getALlCategories(count: Int, after: String): Result<CategoryResponse, Failure>

    suspend fun getIconSetOfCategory(
        identifier: String,
        count: Int,
        after: String,
        showOnlyFree:Boolean
    ): Result<IconSetsResponse, Failure>
}
