package kakcho.test.iconfinder.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kakcho.test.core.data.model.entities.Category
import kakcho.test.core.data.model.network.ErrorResponse
import kakcho.test.core.data.model.network.Failure
import kakcho.test.core.data.model.response.CategoryResponse
import kakcho.test.core.domain.usecase.GetAllCategoriesUseCase
import kakcho.test.iconfinder.R
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class CategoryViewModel(private val getAllCategoriesUseCase: GetAllCategoriesUseCase) :
    ViewModel() {

    private val _isInSearchMode = MutableLiveData<Boolean>(false)
    val isInSearchMode: LiveData<Boolean>
        get() = _isInSearchMode

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _contentLoading = MutableLiveData<Boolean>()
    val contentLoading: LiveData<Boolean>
        get() = _contentLoading


    private val _error = MutableLiveData<Pair<Failure, ErrorResponse?>>()
    val error: LiveData<Pair<Failure, ErrorResponse?>>
        get() = _error

    private val _categoryData = MutableLiveData<List<Category>>()
    val categoryData: LiveData<List<Category>>
        get() = _categoryData


    /**
     * By harnessing the leverage of data binding
     * we are directly calling [toggleSearchViewVisibility]
     * from onclick method present in [R.layout.fragment_category]
     * and updating the values accordingly
     */
    fun toggleSearchViewVisibility() {
        _isInSearchMode.value?.let {
            _isInSearchMode.postValue(!it)
        }
    }

    /**
     * Used to load all categories
     *
     * @param count Int -> number of items to be fetched
     * @param after String -> used to pagination , identifier of last category
     */
    fun loadCategories(count: Int, after: String) {
        viewModelScope.launch {
            if (after == "")
                _loading.postValue(true)
            else
                _contentLoading.postValue(true)
            Timber.d("Loading data after $after")
            val response = getAllCategoriesUseCase.invoke(count, after);
            response.successOrError(::handleCategoryFetchSuccess, ::handleCategoryFetchError)
        }
    }

    /**
     * Used to handle tasks after fetching categories data
     * successfully from api.
     *
     * @param response CategoryResponse
     */
    private fun handleCategoryFetchSuccess(response: CategoryResponse) {
        if (_contentLoading.value == true)
            _contentLoading.postValue(false)
        else
            _loading.postValue(false)
        _categoryData.postValue(response.data)
    }

    /**
     * Used to handle tasks after error in fetching categories data
     */
    private fun handleCategoryFetchError(failure: Failure, error: ErrorResponse?) {

        if (_contentLoading.value == true)
            _contentLoading.postValue(false)
        else
            _loading.postValue(false)
        _error.postValue(Pair(failure, error))
    }

    /**
     * We are performing a local search here
     *
     * @param query String -> input by user
     * @param categoryList List<Category> -> current categories which are being shown to user
     * @return List<Category> -> search result i.e all items which contain user query in the category name
     */
    fun searchCategories(query: String, categoryList: List<Category>): List<Category> {
        val ansList: ArrayList<Category> = ArrayList()
        categoryList.forEach {
            if (it.name.lowercase(Locale.getDefault())
                    .contains(query.lowercase(Locale.getDefault()).trim())
            )
                ansList.add(it)
        }
        return ansList
    }
}