package kakcho.test.iconfinder.ui.category

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kakcho.test.core.data.model.entities.Category
import kakcho.test.iconfinder.R
import kakcho.test.iconfinder.base.BaseFragment
import kakcho.test.iconfinder.categoryRecycler
import kakcho.test.iconfinder.databinding.FragmentCategoryBinding
import kakcho.test.iconfinder.extensions.navigateToDestination
import kakcho.test.iconfinder.extensions.showToast
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {

    companion object {
        fun newInstance() = CategoryFragment()
        const val SELECTED_CATEGORY_IDENTIFIER = "selectedCategoryIdentifier"
    }

    @JvmField
    val LOAD_DATA_COUNT: Int = 20;

    private var searchJob: Job? = null
    private var isInSearchMode = false;
    private val categoriesList: ArrayList<Category> = ArrayList()


    private val viewModel: CategoryViewModel by viewModel()

    override fun setViewModel() {
        binding.viewModel = viewModel;
    }

    override fun setData() {
        loadCategories()
    }

    override fun setListeners() {

        binding.searchView.addTextChangedListener {
            if (it.toString().isEmpty()) {
                searchJob?.cancel()
                return@addTextChangedListener
            }
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(750)
                val searchResult = viewModel.searchCategories(it.toString(), categoriesList)
                setDataToRecyclerView(searchResult)
            }
        }


        binding.epoxyRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                var completelyVisibleItemIndex: Int =
                    layoutManager.findLastCompletelyVisibleItemPosition()
                if (completelyVisibleItemIndex < 0) {
                    completelyVisibleItemIndex = 0
                }

                checkIfDataLoadingRequired(completelyVisibleItemIndex)
            }
        })
    }

    override fun setObservers() {
        viewModel.isInSearchMode.observe(this, { isInSearchMode ->
            this.isInSearchMode = isInSearchMode

            if (!isInSearchMode) {
                closeSearchView()
            } else {
                openSearchView()
            }
        })

        viewModel.categoryData.observe(this, {
            it?.let { list ->
                if (list.isEmpty()) {
                    requireContext().showToast(R.string.error_empty_response.toStringFromResourceId())
                } else {
                    list.forEach { category ->
                        if (!categoriesList.contains(category))
                            categoriesList.add(category)
                    }
                    setDataToRecyclerView(categoriesList)
                }
            }
        })

        viewModel.error.observe(this, {
            reportError(it)
        })
    }


    /**
     * Used to check that has user reached the end of data :
     *
     * if YES -> then we will fetch more data , and this time
     * we will be using after value as we need to fetch only
     * new data , so the identifier of last category is being
     * passed to api in order to get next categories in result
     *
     * @param completelyVisibleItemIndex Int -> index of completely visible item to user
     */
    private fun checkIfDataLoadingRequired(completelyVisibleItemIndex: Int) {

        if (completelyVisibleItemIndex >= categoriesList.size - 2) {
            viewModel.loadCategories(
                LOAD_DATA_COUNT,
                categoriesList[categoriesList.size - 1].identifier
            )
        }
    }


    /**
     * usedLoad categories data when the fragment is created
     * so here in the after part we are sending empty identifier
     */
    private fun loadCategories() {
        viewModel.loadCategories(LOAD_DATA_COUNT, "")
    }


    /**
     * Using [Epoxy] Declarative UI to show items , there are some benefits
     * i) we get DiffUtil out of the box
     * ii) State Management handled out of the box
     *
     * @param iconList List<Icon>? -> Fetched items from api which we need to show
     */
    private fun setDataToRecyclerView(categoryList: List<Category>?) {
        categoryList?.distinct().let {
            binding.epoxyRecyclerview.withModels {
                categoryList?.forEachIndexed() { pos, _ ->
                    return@forEachIndexed categoryRecycler {
                        id(pos)
                        name(categoryList[pos].name)
                        onCategoryClick { _ ->
                            navigateToCategoryIconSets(categoryList[pos].identifier)
                        }
                    }
                }
            }
        }
    }


    /**
     * When user clicks on a category then we navigate the user
     * to the next screen [R.id.selectedCategoryIconSetsFragment]
     *
     * @param identifier String -> identifier of the selected category is being passed
     * so that in the next fragment we can fetch data accordingly and can show.
     */
    private fun navigateToCategoryIconSets(identifier: String) {
        navigateToDestination(
            R.id.selectedCategoryIconSetsFragment,
            R.id.action_categoryFragment_to_selectedCategoryIconSetsFragment,
            bundleOf(SELECTED_CATEGORY_IDENTIFIER to identifier)
        )
    }


    private fun closeSearchView() {
        binding.searchView.text = null
        setDataToRecyclerView(categoryList = categoriesList)
        setKeyboardVisibility(false)
    }

    private fun openSearchView() {
        binding.searchView.requestFocus()
        setKeyboardVisibility(true)
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
            context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager

        if (isVisible)
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        else
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }


}