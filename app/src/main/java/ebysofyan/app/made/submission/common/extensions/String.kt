package ebysofyan.app.made.submission.common.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

/**
 * Created by @ebysofyan on 7/2/19
 */

@SuppressLint("SimpleDateFormat")
fun String.toDateFormat(pattern: String = "MMM dd, yyyy"): String {
    val date = SimpleDateFormat("MM/dd/yyyy").parse(this)
    return SimpleDateFormat(pattern).format(date)
}