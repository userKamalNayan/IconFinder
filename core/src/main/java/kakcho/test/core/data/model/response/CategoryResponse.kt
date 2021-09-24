package kakcho.test.core.data.model.response

import com.google.gson.annotations.SerializedName
import kakcho.test.core.data.model.entities.Category

/**
 * Created by Kamal Nayan on 24-09-2021 at 23:04
 */
data class CategoryResponse(@SerializedName("categories") val data: List<Category>)