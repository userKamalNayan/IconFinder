package kakcho.test.core.data.model.response

import com.google.gson.annotations.SerializedName
import kakcho.test.core.data.model.entities.IconSet

/**
 * Created by Kamal Nayan on 25-09-2021 at 02:28
 */

data class IconSetsResponse(@SerializedName("iconsets") val data: List<IconSet>)
