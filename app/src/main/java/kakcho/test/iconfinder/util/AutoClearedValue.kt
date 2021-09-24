package kakcho.test.iconfinder.util

/**
 * Created by Kamal Nayan on 24-09-2021 at 21:18
 */


import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Used to automatically clear the binding onDestroy to mitigate memory leaks
 * and also saves us from unbinding every time in each fragment
 *
 *
 * @param T : Any  - to make this class generic
 * @property fragment Fragment - fragment in which binding to be done
 * @property _value T?
 * @constructor
 */
class AutoClearedValue<T : Any>(val fragment: Fragment) : ReadWriteProperty<Fragment, T> {
    private var _value: T? = null

    init {
        var observerRegistered = false
        val viewObserver = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyView() {
                observerRegistered = false
                _value = null
            }
        }

        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                if (!observerRegistered) {
                    fragment.viewLifecycleOwner.lifecycle.addObserver(viewObserver)
                    observerRegistered = true
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
            "should never call auto-cleared-value get when it might not be available"
        )
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)
