package kakcho.test.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kamal Nayan on 22-09-2021 at 10:52
 */
data class RasterSize(
    @SerializedName("size_height")
    val sizeHeight: String,

    val size: String,

    @SerializedName("size_width")
    val sizeWidth: String,

    val formats: List<Format>
):BaseModel()
