package kakcho.test.iconfinder.extensions

import android.content.Context
import android.widget.Toast

/**
 * Created by Kamal Nayan on 23-09-2021 at 00:12
 */

fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, length).show();
}