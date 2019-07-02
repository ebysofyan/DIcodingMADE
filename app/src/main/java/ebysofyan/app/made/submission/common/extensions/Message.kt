package ebysofyan.app.made.submission.common.extensions

import android.content.Context
import android.widget.Toast

/**
 * Created by @ebysofyan on 7/2/19
 */

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}