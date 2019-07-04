package ebysofyan.app.made.submission.common.utils

import com.google.gson.Gson
import com.google.gson.JsonParseException
import okhttp3.ResponseBody

/**
 * Created by @ebysofyan on 6/21/19
 */
object ErrorBodyParser {
    fun <T> parse(responseBody: ResponseBody?, clz: Class<T>): T {
        val errorJsonString = responseBody?.string()
        return try {
            Gson().fromJson(errorJsonString, clz)
        } catch (e: JsonParseException) {
            throw JsonParseException("Unparseable error response")
        }
    }
}