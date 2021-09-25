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
     * Used to perform search operation on the query entered by user
     * and then invokes [handleIconFetchSuccess] [handleIconFetchError]
     * functions according to the response received
     *
     *  @param query String -> Input provided by user
     */
//    fun searchIcon(query: String) {
//        viewModelScope.launch {
//            _loading.postValue(true)
//            val result =searchIconUseCase.invoke(query)
//            result.successOrError(::handleIconFetchSuccess, ::handleIconFetchError)
//        }
//    }





}