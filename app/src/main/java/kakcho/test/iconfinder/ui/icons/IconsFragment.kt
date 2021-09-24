package kakcho.test.iconfinder.ui.icons

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kakcho.test.iconfinder.R

class IconsFragment : Fragment() {

    companion object {
        fun newInstance() = IconsFragment()
    }

    private lateinit var viewModel: IconsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_icons, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IconsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}