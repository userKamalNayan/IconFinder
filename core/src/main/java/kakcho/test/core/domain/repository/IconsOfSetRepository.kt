package kakcho.test.core.domain.repository

import kakcho.test.core.data.model.network.Failure
import kakcho.test.core.data.model.network.Result
import kakcho.test.core.data.model.response.IconsResponse

/**
 * Created by Kamal Nayan on 22-09-2021 at 10:59
 */
interface IconsOfSetRepository {
    suspend fun getIconsOfSet(setID:String): Result<IconsResponse, Failure>
    suspend fun searchIcon(query:String):Result<IconsResponse,Failure>
}