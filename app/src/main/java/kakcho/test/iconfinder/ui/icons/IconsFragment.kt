package kakcho.test.iconfinder.ui.icons


import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kakcho.test.core.data.model.Icon
import kakcho.test.core.data.model.RasterSize
import kakcho.test.iconfinder.R
import kakcho.test.iconfinder.base.BaseFragment
import kakcho.test.iconfinder.databinding.FragmentIconsBinding
import kakcho.test.iconfinder.downloadSizesRecyclerItem
import kakcho.test.iconfinder.extensions.getValueNonNull
import kakcho.test.iconfinder.extensions.showToast
import kakcho.test.iconfinder.iconsRecycler
import kakcho.test.iconfinder.ui.selectedcategory.SelectedCategoryIconSetsFragment
import kakcho.test.iconfinder.util.DownloadUtil
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class IconsFragment : BaseFragment<FragmentIconsBinding>(R.layout.fragment_icons) {

    companion object {
        fun newInstance() = IconsFragment()
    }

    private var offset = 0;
    private var iconsList: ArrayList<Icon> = ArrayList()
    private var selectedSizeForDownload: RasterSize? = null
    var dataCount: Int = 20;
    private val viewModel: IconsViewModel by viewModel()
    private val downloadUtil: DownloadUtil by inject()

    private val iconSetId by getValueNonNull<String>(
        SelectedCategoryIconSetsFragment.SELECTED_ICON_SET_ID,
        "NA"
    )


    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun setData() {
        loadIconsData()
        setToolbar(R.string.toolbar_text_icons.toStringFromResourceId())
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
                checkIfDataLoadingRequired(completelyVisibleItemIndex, layoutManager.itemCount)
            }
        })

    }

    override fun setObservers() {
        viewModel.iconsData.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                context?.showToast(R.string.error_empty_response.toStringFromResourceId())
            } else {
                it.forEach { icon ->
                    if (!iconsList.contains(icon))
                        iconsList.add(icon)
                }
                setDataToRecyclerView(iconsList)
            }
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
    private fun checkIfDataLoadingRequired(completelyVisibleItemIndex: Int, totalItems: Int) {

        if (completelyVisibleItemIndex + 1 == totalItems) {
            loadIconsData()
        }
    }



    private fun setDataToRecyclerView(iconsList: List<Icon>) {
        binding.epoxyRecyclerview.withModels {
            iconsList?.forEachIndexed { pos, _ ->
                return@forEachIndexed iconsRecycler {
                    id(pos)
                    icon(iconsList[pos])

                    onItemClick { view ->
                        when (view.id) {
                            R.id.download_icon -> {
                                handleDownloadClick(iconsList[pos])
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Shows BottomSheet Dialog and all the
     * available sizes are shown in a recycler view
     *
     * @param icon Icon -> Icon object for which download pressed
     */

    private fun handleDownloadClick(icon: Icon) {
        val bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.bottom_sheet_download_icon)
        bottomSheet.setCanceledOnTouchOutside(true)
        bottomSheet.behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        val sizeRecyclerView =
            bottomSheet.findViewById<EpoxyRecyclerView>(R.id.bts_epoxyRecyclerView)
        val downloadButton = bottomSheet.findViewById<AppCompatButton>(R.id.bts_btn_download)

        showRasterSizes(sizeRecyclerView, icon)
        setDownloadClickListener(downloadButton, bottomSheet)
        bottomSheet.setOnDismissListener {
            selectedSizeForDownload = null
        }

        bottomSheet.show()
    }

    /**
     * Checks either a size is selected or not and if selected then
     * forwards url and other required data to [proceedToDownloadProcess]
     *
     * @param downloadButton AppCompatButton? -> button to whom listener is attached
     * @param bottomSheet BottomSheetDialog -> bottomSheet dialog, in order to forward
     * to next functions
     */
    private fun setDownloadClickListener(
        downloadButton: AppCompatButton?,
        bottomSheet: BottomSheetDialog
    ) {
        downloadButton?.setOnClickListener {
            if (selectedSizeForDownload == null) {
                context?.showToast(R.string.download_size_instruction.toStringFromResourceId())
            } else {
                val downloadUrl = selectedSizeForDownload!!.formats[0].previewUrl
                val ext = selectedSizeForDownload!!.formats[0].format
                proceedToDownloadProcess(downloadUrl, ext, bottomSheet)
            }
        }
    }

    /**
     * Used for checking prerequisites like
     * storage permission and if available
     * then we are initializing download
     *
     * @param downloadUrl String -> url of file
     * @param ext String -> extension of file
     * @param bottomSheet BottomSheetDialog -> bottom sheet , in order to dismiss
     * when download is enqueued.
     *
     */
    private fun proceedToDownloadProcess(
        downloadUrl: String,
        ext: String,
        bottomSheet: BottomSheetDialog
    ) {
        checkStoragePermission() {
            downloadUtil.download(downloadUrl, ext) {
                context?.showToast("Download Started")
                bottomSheet.dismiss()
            }
        }
    }

    /**
     * This is a [HigherOrderFunction] Used to check that
     * either storage permission is available or not and
     * [storageAccessible] is invoked to send callback
     * if we permission is granted
     *
     * @param storageAccessible Function0<Unit> -> callback function
     */
    private fun checkStoragePermission(storageAccessible: () -> Unit) {
        permissionsBuilder(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .build()
            .send { result ->
                if (result.allGranted()) {
                    storageAccessible()
                } else {
                    context?.showToast(
                        "Storage Permission Required"
                    )
                }
            }
    }


    /**
     * Used to set data to recycler view which shows all available
     * raster sizes which can be downloaded
     *
     * @param sizeRecyclerView EpoxyRecyclerView? -> Recycler view in which data is being shown
     * @param icon Icon -> Icon object whose raster sizes will be shown
     */
    private fun showRasterSizes(sizeRecyclerView: EpoxyRecyclerView?, icon: Icon) {
        sizeRecyclerView?.withModels {
            icon.rasterSizes.forEachIndexed { pos, _ ->
                val currentSize = icon.rasterSizes[pos]
                return@forEachIndexed downloadSizesRecyclerItem {
                    id(pos)
                    rasterSize(currentSize)
                    selectedSize(selectedSizeForDownload)

                    onItemClick() { v ->
                        selectedSizeForDownload = icon.rasterSizes[pos]
                        sizeRecyclerView.requestModelBuild()
                    }
                }
            }
        }
    }


    /**
     * Load icons data of a selected icon set
     */
    private fun loadIconsData() {
        viewModel.fetchIconsOfSet(iconSetId, dataCount, offset)
        offset += dataCount
        dataCount += dataCount
    }
}