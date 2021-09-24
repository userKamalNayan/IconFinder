package kakcho.test.iconfinder

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
    private var searchJob: Job? = null
    private var isInSearchMode = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_IconFinder)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setViewModel()
        setObservers()
        setListeners()
        loadData()
    }


    /**
     * Added debounce in search operation
     * to optimize api calls and to prevent
     * screen flickering
     */
    private fun setListeners() {
        binding.searchView.addTextChangedListener {
            if (it.toString().isEmpty()) {
                searchJob?.cancel()
                loadData()
                return@addTextChangedListener
            }
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(750)
                viewModel.searchIcon(it.toString())
            }
        }
    }


    /**
     * Assigning view model in order
     * to use data binding
     */
    private fun setViewModel() {
        binding.mainViewModel = viewModel
    }


    /**
     * Observing variables of view model
     * in order to update ui adn perform
     * operations accordingly
     */
    private fun setObservers() {
        viewModel.isInSearchMode.observe(this, { isInSearchMode ->
            this.isInSearchMode = isInSearchMode

            if (!isInSearchMode) {
                closeSearchView()
            } else {
                openSearchView()
            }
        })

        viewModel.iconsData.observe(this, {
            it?.let {
                if (it.isEmpty()) {
                    showToast("Nothing to Show")
                } else {
                    setDataToRecyclerView(it)
                }
            }
        })

        viewModel.error.observe(this, {
            reportError(it)
        })
    }


    /**
     * Used to Load data of a specific icon set
     * For the time being we have hardcoded the
     * icon set id value.
     *
     */
    private fun loadData() {
        viewModel.fetchIconsOfSet("182935")
    }


    /**
     *
     * Reporting error using the log method which is overrided
     * here @see[kakcho.test.core.exceptionhandling.ReleaseTree]
     * and apprising user at the same time using toast.
     *
     */
    private fun reportError(it: Pair<Failure, ErrorResponse?>?) {
        Timber.log(Log.ERROR, it?.second?.message)
        showToast(it?.second?.exception.toString(), Toast.LENGTH_SHORT)
    }


    private fun closeSearchView() {
        binding.searchView.text = null
        setKeyboardVisibility(false)
    }

    private fun openSearchView() {
        binding.searchView.requestFocus()
        setKeyboardVisibility(true)
    }


    /**
     * Using [Epoxy] Declarative UI to show items , there are some benefits
     * i) we get DiffUtil out of the box
     * ii) State Management handled out of the box
     *
     * @param iconList List<Icon>? -> Fetched items from api which we need to show
     */
    private fun setDataToRecyclerView(iconList: List<Icon>?) {
        iconList?.let {
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
    }


    /**
     * Used to show and hide keyboard according to need
     * When user clicks on search icon then we automatically
     * show the keyboard and vice-versa for close click
     *
     * @param isVisible Boolean -> is to show keyboard or not
     */
    private fun setKeyboardVisibility(isVisible: Boolean) {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        if (isVisible)
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        else
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }


    /**
     * To handle back press when user is in
     * search mode , that time we need to
     * close search mode
     */
    override fun onBackPressed() {
        if (isInSearchMode) {
            viewModel.toggleSearchViewVisibility()
        }
    }

}