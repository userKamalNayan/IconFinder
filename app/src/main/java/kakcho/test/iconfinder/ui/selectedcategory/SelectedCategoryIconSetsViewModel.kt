package kakcho.test.iconfinder.ui.selectedcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kakcho.test.core.data.model.entities.IconSet
import kakcho.test.core.data.model.network.ErrorResponse
import kakcho.test.core.data.model.network.Failure
import kakcho.test.core.data.model.response.IconSetsResponse
import kakcho.test.core.domain.usecase.GetIconSetOfSelectedCategoryUseCase
import kotlinx.coroutines.launch

class SelectedCategoryIconSetsViewModel(private val getIconSetOfSelectedCategoryUseCase: GetIconSetOfSelectedCategoryUseCase) :
    ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _contentLoading = MutableLiveData<Boolean>()
    val contentLoading: LiveData<Boolean>
        get() = _contentLoading


    private val _error = MutableLiveData<Pair<Failure, ErrorResponse?>>()
    val error: LiveData<Pair<Failure, ErrorResponse?>>
        get() = _error

    private val _iconSetData = MutableLiveData<List<IconSet>>()
    val iconSetData: LiveData<List<IconSet>>
        get() = _iconSetData


    fun getIconSets(identifier: String, count: Int, after: String,showOnlyFree:Boolean) {
        viewModelScope.launch {
            if (after == "")
                _loading.postValue(true)
            else
                _contentLoading.postValue(true)

            val response = getIconSetOfSelectedCategoryUseCase.invoke(identifier, count, after,showOnlyFree)
            response.successOrError(::handleIconSetFetchSuccess, ::handleIconSetFetchError)
        }
    }

    /**
     * Used to handle tasks after fetching categories data
     * successfully from api.
     *
     * @param response CategoryResponse
     */
    private fun handleIconSetFetchSuccess(response: IconSetsResponse) {
        if (_contentLoading.value == true)
            _contentLoading.postValue(false)
        else
            _loading.postValue(false)
        _iconSetData.postValue(response.data)
    }

    /**
     * Used to handle tasks after error in fetching categories data
     */
    private fun handleIconSetFetchError(failure: Failure, error: ErrorResponse?) {

        if (_contentLoading.value == true)
            _contentLoading.postValue(false)
        else
            _loading.postValue(false)
        _error.postValue(Pair(failure, error))
    }

}