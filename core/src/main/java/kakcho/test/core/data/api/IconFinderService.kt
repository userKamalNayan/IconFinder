package kakcho.test.core.data.api

import kakcho.test.core.data.model.response.IconsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Kamal Nayan on 22-09-2021 at 03:27
 */
interface IconFinderService {

    @GET("iconsets/{setId}/icons")
    suspend fun getIcons(
        //   @Header("Authorization") api: String,
        @Path("setId") setId: String,
    ): Response<IconsResponse>


    @GET("icons/search")
    suspend fun searchIcon(
        //   @Header("Authorization") api: String,
        @Query("query") query: String,
        @Query("count") count: Int,
    ): Response<IconsResponse>
}