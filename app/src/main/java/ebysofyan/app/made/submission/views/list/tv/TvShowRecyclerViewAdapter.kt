package ebysofyan.app.made.submission.views.list.tv

import android.view.View
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.base.BaseRecyclerViewAdapter
import ebysofyan.app.made.submission.common.extensions.loadWithGlidePlaceholder
import ebysofyan.app.made.submission.common.extensions.toDateFormat
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.data.TvShow
import kotlinx.android.synthetic.main.movie_list_item.view.*

/**
 * Created by @ebysofyan on 7/2/19
 */
class TvShowRecyclerViewAdapter(
    private val onClickListener: (View, TvShow, Int) -> Unit
) : BaseRecyclerViewAdapter<TvShow>() {
    override fun getLayoutResourceId(): Int = R.layout.movie_list_item

    override fun onBindItem(view: View, data: TvShow, position: Int) {
        view.movie_item_image.loadWithGlidePlaceholder(Constants.getImageUrl(fileName = data.posterPath))
        view.movie_item_title.text = data.name
        view.movie_item_rating.text = data.voteAverage.toString()
        view.movie_item_release_date.text = data.firstAirDate.toDateFormat()

        view.setOnClickListener {
            onClickListener.invoke(view, data, position)
        }
    }
}