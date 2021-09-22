package kakcho.test.core.data.model.response

import com.google.gson.annotations.SerializedName
import kakcho.test.core.data.model.Icon

/**
 * Created by Kamal Nayan on 22-09-2021 at 10:55
 */
data class IconsResponse(
    @SerializedName("icons")
    val data: List<Icon>
)
