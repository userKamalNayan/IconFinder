package kakcho.test.core.data.api

import kakcho.test.core.data.model.response.CategoryResponse
import kakcho.test.core.data.model.response.IconSetsResponse
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


    /**
     * Used to fetch all categories available
     * @see[https://developer.iconfinder.com/reference/list-all-categories]
     *
     * @param count Int -> Number of items to retrieve
     * @param after String -> used for pagination, the id of element after which next elements should be fetched
     * @return Response<CategoryResponse>
     */
    @GET("categories")
    suspend fun getAllCategories(
        @Query("count") count: Int,
        @Query("after") after: String
    ): Response<CategoryResponse>



    @GET("categories/{category_identifier}/iconsets")
    suspend fun getIconSetsOfCategory(
        @Path("category_identifier") identifier: String,
        @Query("count") count: Int,
        @Query("after") after: String
    ): Response<IconSetsResponse>
}