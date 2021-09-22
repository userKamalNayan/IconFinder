package kakcho.test.iconfinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kakcho.test.core.data.model.Icon
import kakcho.test.core.data.model.network.ErrorResponse
import kakcho.test.core.data.model.network.Failure
import kakcho.test.core.data.model.response.IconsResponse
import kakcho.test.core.domain.usecase.GetIconsOfSetUseCase
import kakcho.test.core.domain.usecase.SearchIconUseCase
import kotlinx.coroutines.launch

/**
 * Created by Kamal Nayan on 22-09-2021 at 00:51
 */

class MainViewModel(
    private val getIconsOfSetUseCase: GetIconsOfSetUseCase,
    private val searchIconUseCase: SearchIconUseCase
) : ViewModel() {

    private val _isInSearchMode = MutableLiveData<Boolean>(false)
    val isInSearchMode: LiveData<Boolean>
        get() = _isInSearchMode

    private val _iconsData = MutableLiveData<List<Icon>>()
    val iconsData: LiveData<List<Icon>>
        get() = _iconsData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Pair<Failure, ErrorResponse?>>()
    val error: LiveData<Pair<Failure, ErrorResponse?>>
        get() = _error


    /**
     * By harnessing the leverage of data binding
     * we are directly calling [toggleSearchViewVisibility]
     * from onclick method present in [R.layout.activity_main]
     * and updating the values accordingly
     */
    fun toggleSearchViewVisibility() {
        _isInSearchMode.value?.let {
            _isInSearchMode.postValue(!it)
        }
    }

    /**
     * Used to fetch icons of a specific set and further
     * invokes [handleIconFetchSuccess] [handleIconFetchError]
     * functions according to the response received
     */
    fun fetchIconsOfSet(iconSetID: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result = getIconsOfSetUseCase.invoke(iconSetID)
            result.successOrError(::handleIconFetchSuccess, ::handleIconFetchError)
        }
    }

    /**
     * Used to perform search operation on the query entered by user
     * and then invokes [handleIconFetchSuccess] [handleIconFetchError]
     * functions according to the response received
     *
     *  @param query String -> Input provided by user
     */
    fun searchIcon(query: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            val result =searchIconUseCase.invoke(query)
            result.successOrError(::handleIconFetchSuccess, ::handleIconFetchError)
        }
    }

    /**
     * Used to handle success case, i.e
     * when we have successfully fetched data from api
     * then handling the further operations and tasks
     *
     * @param response IconsResponse -> data fetched from api
     */
    private fun handleIconFetchSuccess(response: IconsResponse) {
        _loading.postValue(false)
        _iconsData.postValue(response.data)
    }

    /**
     * Used to handle failure case, i.e
     * when we have encountered any exception
     * so in this function we perform further tasks
     * which should be done after encountering failure
     *
     * @param failure Failure -> Type of failure occurred
     * @param error ErrorResponse? -> Exception and message
     */
    private fun handleIconFetchError(failure: Failure, error: ErrorResponse?) {
        _loading.postValue(false)
        _error.postValue(Pair(failure, error))
    }



}