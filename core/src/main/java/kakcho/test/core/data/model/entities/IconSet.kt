package kakcho.test.core.data.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Kamal Nayan on 25-09-2021 at 02:24
 */
data class IconSet(
    @SerializedName("iconset_id") val iconSetId: String,
    @SerializedName("name") val iconSetName: String
)
