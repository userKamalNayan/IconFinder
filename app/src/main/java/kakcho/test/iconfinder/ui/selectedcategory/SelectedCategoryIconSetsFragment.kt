package kakcho.test.iconfinder.ui.selectedcategory

import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kakcho.test.core.data.model.entities.IconSet
import kakcho.test.iconfinder.R
import kakcho.test.iconfinder.base.BaseFragment
import kakcho.test.iconfinder.categoryRecycler
import kakcho.test.iconfinder.databinding.FragmentSelectedCategoryIconSetsBinding
import kakcho.test.iconfinder.extensions.getValueNonNull
import kakcho.test.iconfinder.extensions.navigateToDestination
import kakcho.test.iconfinder.extensions.showToast
import kakcho.test.iconfinder.ui.category.CategoryFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectedCategoryIconSetsFragment :
    BaseFragment<FragmentSelectedCategoryIconSetsBinding>(R.layout.fragment_selected_category_icon_sets) {


    companion object {
        fun newInstance() = SelectedCategoryIconSetsFragment()
        const val SELECTED_ICON_SET_ID = "selectedIconSetId"
    }

    private val iconSetList: ArrayList<IconSet> = ArrayList()

    @JvmField
    val LOAD_DATA_COUNT: Int = 20;


    private val viewModel: SelectedCategoryIconSetsViewModel by viewModel()

    private val identifier by getValueNonNull<String>(
        CategoryFragment.SELECTED_CATEGORY_IDENTIFIER,
        "NA"
    )

    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun setData() {
        setToolbar(identifier)
        loadData(identifier, count = LOAD_DATA_COUNT, after = "")
    }


    override fun setListeners() {
        binding.epoxyRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                var completelyVisibleItemIndex: Int =
                    layoutManager.findLastCompletelyVisibleItemPosition()
                if (completelyVisibleItemIndex < 0) {
                    completelyVisibleItemIndex = 0
                }

                checkIfDataLoadingRequired(completelyVisibleItemIndex)
            }
        })
    }

    override fun setObservers() {
        viewModel.iconSetData.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                context?.showToast(R.string.error_empty_response.toStringFromResourceId())
            } else {
                it?.forEach { iconSet ->
                    if (!iconSetList.contains(iconSet))
                        iconSetList.add(iconSet)
                }
                setDataToRecyclerView(iconSetList)
            }
        })

        viewModel.error.observe(this, {
            reportError(it)
        })
    }


    /**
     * Used to check that has user reached the end of data :
     *
     * if YES -> then we will fetch more data , and this time
     * we will be using after value as we need to fetch only
     * new data , so the identifier of last category is being
     * passed to api in order to get next categories in result
     *
     * @param completelyVisibleItemIndex Int -> index of completely visible item to user
     */
    private fun checkIfDataLoadingRequired(completelyVisibleItemIndex: Int) {

        if (completelyVisibleItemIndex >= iconSetList.size - 2) {
            viewModel.getIconSets(
                identifier,
                LOAD_DATA_COUNT,
                iconSetList[iconSetList.size - 1].iconSetId
            )
        }
    }

    private fun setDataToRecyclerView(iconSetList: ArrayList<IconSet>) {
        iconSetList?.distinct().let {
            binding.epoxyRecyclerview.withModels {
                iconSetList.forEachIndexed { pos, _ ->
                    return@forEachIndexed categoryRecycler {
                        id(pos)
                        name(iconSetList[pos].iconSetName)
                        onCategoryClick { _ ->
                            navigateToIconsOfIconSet(iconSetList[pos].iconSetId)
                        }
                    }
                }
            }
        }

    }

    private fun navigateToIconsOfIconSet(iconSetId: String) {
        navigateToDestination(
            R.id.iconsFragment, R.id.action_selectedCategoryIconSetsFragment_to_iconsFragment,
            bundleOf(SELECTED_ICON_SET_ID to iconSetId)
        )

    }

    private fun loadData(identifier: String, count: Int, after: String) {
        viewModel.getIconSets(identifier, count, after)
    }

}