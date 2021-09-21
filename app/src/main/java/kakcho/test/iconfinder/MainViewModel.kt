package kakcho.test.iconfinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Kamal Nayan on 22-09-2021 at 00:51
 */

class MainViewModel : ViewModel() {
    private val _isInSearchMode = MutableLiveData<Boolean>(false)
    val isInSearchMode: LiveData<Boolean>
        get() = _isInSearchMode

    fun toggleSearchViewVisibility() {
        _isInSearchMode.value?.let {
            _isInSearchMode.postValue(!it)
        }

    }
}