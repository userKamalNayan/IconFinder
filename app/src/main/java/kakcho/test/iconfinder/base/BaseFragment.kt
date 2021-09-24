package kakcho.test.iconfinder.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import kakcho.test.iconfinder.MainActivity
import kakcho.test.iconfinder.util.autoCleared

/**
 * Created by Kamal Nayan on 24-09-2021 at 21:17
 */


/**
 *
 *
 * @param T : ViewDataBinding - Binding of fragment which is extending this class and T means it's generic
 *
 * @property contentLayoutId Int - id of layout of that fragment
 * @property rootView View - not in use yet
 * @property binding T - binding view using the autoCleared class, so that we don't need to unbind everytime
 * @property activity HomeActivity - parent activity
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val contentLayoutId: Int) :
    Fragment() {

    protected lateinit var rootView: View
    protected var binding by autoCleared<T>()
    private lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }


    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<T>(inflater, contentLayoutId, container, false).also {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            binding = it

        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        setViewModel()
        setData()
        setListeners()
        setObservers()
    }


    /** @author - Kamal Nayan   @version - 1.0
     *
     * @return String value from the corresponding string resource id
     */
    protected fun Int.toStringFromResourceId(): String =
        context?.resources?.getString(this) ?: ""


    /**
     *@author - Kamal Nayan      @version - 1.0
     *
     *  Implement in fragment which is extending BaseFragment to set view model for data binding
     *
     */
    abstract fun setViewModel()


    /**
     * @author - Kamal Nayan    @version - 1.0
     *
     * Implement in fragment which is extending BaseFragment to set data to view or other necessary fields
     *
     */
    abstract fun setData()


    /**
     * @author - Kamal Nayan      @version - 1.0
     *
     * Implement in fragment which is extending BaseFragment to set listeners for views or other necessary fields
     *
     */
    abstract fun setListeners()


    /**
     * @author - Kamal Nayan     @version - 1.0
     *
     * Implement in fragment which is extending BaseFragment to set observers for variables of viewModel or other necessary fields
     *
     */
    abstract fun setObservers()
}