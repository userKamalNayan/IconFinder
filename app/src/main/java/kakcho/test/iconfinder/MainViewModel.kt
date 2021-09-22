package kakcho.test.iconfinder

import timber.log.Timber
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kakcho.test.core.domain.usecase.GetIconsOfSetUseCase
import kotlinx.coroutines.launch

/**
 * Created by Kamal Nayan on 22-09-2021 at 00:51
 */

class MainViewModel(private val getIconsOfSetUseCase: GetIconsOfSetUseCase) : ViewModel() {

    private val _isInSearchMode = MutableLiveData<Boolean>(false)
    val isInSearchMode: LiveData<Boolean>
        get() = _isInSearchMode

    fun toggleSearchViewVisibility() {
        _isInSearchMode.value?.let {
            _isInSearchMode.postValue(!it)
        }
    }

    fun fetchIconsOfSet() {
        viewModelScope.launch {

            Timber.d("icon ->  in fetch Icon");
            getIconsOfSetUseCase.invoke()
        }
    }
}