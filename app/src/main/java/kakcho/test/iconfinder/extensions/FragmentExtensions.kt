package kakcho.test.iconfinder.extensions

/**
 * Created by Kamal Nayan on 25-09-2021 at 00:16
 */
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController

fun Fragment.navigateToDestination(
    destinationId: Int,
    actionId: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    if (isAdded)
        if (this.findNavController().currentDestination?.id != destinationId &&
            this.findNavController().currentDestination?.getAction(actionId) != null
        )
            this.findNavController().navigate(
                actionId,
                bundle,
                navOptions,
                navigatorExtras
            )
}



inline fun <reified T : Any> Fragment.getValue(lable: String, defaultvalue: T? = null) = lazy {
    val value = arguments?.get(lable)
    if (value is T) value else defaultvalue
}


inline fun <reified T : Any> Fragment.getValueNonNull(lable: String, defaultvalue: T? = null) =
    lazy {
        val value = arguments?.get(lable)
        requireNotNull(if (value is T) value else defaultvalue) { lable }
    }
