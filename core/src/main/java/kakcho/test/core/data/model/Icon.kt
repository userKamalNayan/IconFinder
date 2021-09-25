package kakcho.test.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kamal Nayan on 22-09-2021 at 10:49
 */
data class Icon(
    @SerializedName("is_premium")
    val isPremium: Boolean,

    val type: String,

    @SerializedName("raster_sizes")
    val rasterSizes: List<RasterSize>,

    val prices: List<Price>?,

    @SerializedName("icon_id")
    val iconId: Int
):BaseModel()