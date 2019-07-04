package ebysofyan.app.made.submission.common.extensions

import android.annotation.SuppressLint
import android.content.Context
import java.text.SimpleDateFormat

/**
 * Created by @ebysofyan on 7/2/19
 */

@SuppressLint("SimpleDateFormat")
fun String.toDateFormat(pattern: String = "MMMM dd, yyyy"): String {
    val date = SimpleDateFormat("yyyy-MM-dd").parse(this)
    return SimpleDateFormat(pattern).format(date)
}

fun Context.forceLocale2to1() = if (resources?.configuration?.locale?.language.toString() == "in") "id"
else resources?.configuration?.locale?.language.toString()