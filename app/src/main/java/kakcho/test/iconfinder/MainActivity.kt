package kakcho.test.iconfinder

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kakcho.test.iconfinder.databinding.ActivityMainBinding
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
        setViewModel()
        setObservers()
        loadData()
    }

    private fun loadData() {
       System.out.println("icon -> load data called");
        Timber.d("icon -> load data called");
        viewModel.fetchIconsOfSet()
    }

    private fun setViewModel() {
        binding.mainViewModel = viewModel
    }

    private fun setObservers() {
        viewModel.isInSearchMode.observe(this, { isInSearchMode ->
            if (!isInSearchMode) {
                binding.searchView.text = null
                setKeyboardVisibility(false)
            } else {
                binding.searchView.requestFocus()
                setKeyboardVisibility(true)
            }
        })
    }


    private fun setKeyboardVisibility(isVisible: Boolean) {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        if (isVisible)
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        else
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}