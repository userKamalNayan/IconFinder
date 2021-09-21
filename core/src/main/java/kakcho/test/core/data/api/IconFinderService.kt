package kakcho.test.core.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Kamal Nayan on 22-09-2021 at 03:27
 */
interface IconFinderService {
    @GET("iconsets/{setId}/icons")
    fun getIcons(
        @Path("setId") setId: String,
        @Query("client_id") apiClientId: String,
        @Query("client_secret") apiClientSecret: String
    )
}