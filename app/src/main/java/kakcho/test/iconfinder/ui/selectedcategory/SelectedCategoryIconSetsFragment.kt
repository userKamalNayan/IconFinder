package kakcho.test.iconfinder.ui.selectedcategory

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kakcho.test.iconfinder.R

class SelectedCategoryIconSetsFragment : Fragment() {

    companion object {
        fun newInstance() = SelectedCategoryIconSetsFragment()
    }

    private lateinit var viewModel: SelectedCategoryIconSetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_selected_category_icon_sets, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelectedCategoryIconSetsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}