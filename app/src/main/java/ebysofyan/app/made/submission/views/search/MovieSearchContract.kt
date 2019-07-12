package ebysofyan.app.made.submission.views.search

import com.google.gson.JsonElement
import ebysofyan.app.made.submission.base.BasePresenter

/**
 * Created by @ebysofyan on 7/13/19
 */
interface MovieSearchContract {
    interface View {
        fun onLoading(show: Boolean)

        fun onMultiSearchLoaded(jsonElement: JsonElement?)
    }

    interface Presenter : BasePresenter<View> {
        fun searchMulti(query: String)
    }
}