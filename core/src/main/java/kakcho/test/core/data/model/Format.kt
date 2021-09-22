package kakcho.test.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kamal Nayan on 22-09-2021 at 10:53
 */
data class Format(
    val format: String,

    @SerializedName("download_url")
    val downloadUrl: String,

    @SerializedName("preview_url")
    val previewUrl: String
):BaseModel()
