package kakcho.test.iconfinder.bindingadapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kakcho.test.core.data.model.RasterSize
import timber.log.Timber

/**
 * Created by Kamal Nayan on 22-09-2021 at 01:15
 */

/**
 * TO control visibility of views from layout
 * it comes to fruition when we are using data-binding
 *
 * @param view View -> whose visibility needs to be updated {PROVIDED AUTOMATICALLY}
 * @param isVisible Boolean -> if true -> show  else->  hide
 */
@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

/**
 * Used to load images from network using Glide
 * directly from layout and we use it to perform
 * image loading from layout using data-binding
 *
 * @param imageView ImageView -> in which image will be loaded {PROVIDED AUTOMATICALLY}
 * @param url String -> url of image to be loaded
 */
@BindingAdapter("loadImageByUrl")
fun loadImageByUrl(imageView: AppCompatImageView, url: String) {
    Glide.with(imageView).load(url).into(imageView)
}


/**
 * To have horizontal scrolling of text if the text is larger
 */

@BindingAdapter("setSelected")
fun setSelected(view: TextView, isSelected: Boolean) {
    view.isSelected = true
}

@BindingAdapter("setDimension")
fun setDimension(view: TextView, rasterSize: RasterSize) {
    val dimension = "${rasterSize.sizeWidth} * ${rasterSize.sizeHeight}"
    view.text = dimension;
}

@BindingAdapter("setFormattedPrice")
fun setFormattedPrice(view: TextView, price: String) {
    val formattedPrice = "₹ ${price}"
    view.text = formattedPrice;
}

@BindingAdapter("setSelectedBackground")
fun setSelectedBackground(view: View, isSelected: Boolean) {
    if (isSelected)
        view.background = Color.parseColor("#FF158C25").toDrawable()
    else
        view.background = Color.WHITE.toDrawable()
}