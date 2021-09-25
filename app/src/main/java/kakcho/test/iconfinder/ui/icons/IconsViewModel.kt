package kakcho.test.iconfinder.ui.icons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kakcho.test.core.data.model.Icon
import kakcho.test.core.data.model.network.ErrorResponse
import kakcho.test.core.data.model.network.Failure
import kakcho.test.core.data.model.response.IconsResponse
import kakcho.test.core.domain.usecase.GetIconsOfSetUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class IconsViewModel(private val getIconsOfSetUseCase: GetIconsOfSetUseCase) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _contentLoading = MutableLiveData<Boolean>()
    val contentLoading: LiveData<Boolean>
        get() = _contentLoading


    private val _error = MutableLiveData<Pair<Failure, ErrorResponse?>>()
    val error: LiveData<Pair<Failure, ErrorResponse?>>
        get() = _error

    private val _iconsData = MutableLiveData<List<Icon>>()
    val iconsData: LiveData<List<Icon>>
        get() = _iconsData


    /**
     * Used to fetch icons of a specific set and further
     * invokes [handleIconFetchSuccess] [handleIconFetchError]
     * functions according to the response received
     */
    fun fetchIconsOfSet(iconSetID: String, count: Int, offset: Int) {
        Timber.d("offset = $offset")
        viewModelScope.launch {
            if (offset == 0)
                _loading.postValue(true)
            else
                _contentLoading.postValue(true)

            val result = getIconsOfSetUseCase.invoke(iconSetID, count, offset)
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

        if (_contentLoading.value == true)
            _contentLoading.postValue(false)
        else
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

        if (_contentLoading.value == true)
            _contentLoading.postValue(false)
        else
            _loading.postValue(false)
        _error.postValue(Pair(failure, error))
    }
}