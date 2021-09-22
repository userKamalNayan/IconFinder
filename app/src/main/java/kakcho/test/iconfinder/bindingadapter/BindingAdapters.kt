package kakcho.test.iconfinder.bindingadapter

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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
fun loadImageByUrl(imageView: ImageView, url: String) {
    Glide.with(imageView).load(url).into(imageView)
}