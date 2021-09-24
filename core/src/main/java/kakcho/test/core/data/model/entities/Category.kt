package kakcho.test.core.data.model.entities

import kakcho.test.core.data.model.BaseModel

/**
 * Created by Kamal Nayan on 24-09-2021 at 22:49
 */
data class Category(
    val name: String,
    val identifier: String
) : BaseModel()
