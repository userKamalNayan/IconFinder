package kakcho.test.iconfinder

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kakcho.test.core.data.model.Icon
import kakcho.test.core.data.model.network.ErrorResponse
import kakcho.test.core.data.model.network.Failure
import kakcho.test.iconfinder.databinding.ActivityMainBinding
import kakcho.test.iconfinder.extensions.showToast
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }




    /**
     * Assigning view model in order
     * to use data binding
     */
//    private fun setViewModel() {
//        binding.mainViewModel = viewModel
//    }


    /**
     * Observing variables of view model
     * in order to update ui adn perform
     * operations accordingly
     */


    /**
     * Used to Load data of a specific icon set
     * For the time being we have hardcoded the
     * icon set id value.
     *
     */


    /**
     *  iconList?.let {
    binding.epoxyRecyclerview.withModels {
    iconList.forEachIndexed() { pos, _ ->
    if (iconList[pos].isPremium)
    return@forEachIndexed iconsRecycler {
    id(pos)
    icon(iconList[pos])
    }
    }
    }
    }
     */






    /**
     * To handle back press when user is in
     * search mode , that time we need to
     * close search mode
     */

//        if (isInSearchMode) {
//            viewModel.toggleSearchViewVisibility()
//        }
    }
