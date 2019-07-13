package ebysofyan.app.made.favorite.views.list

import android.view.View
import ebysofyan.app.made.favorite.R
import ebysofyan.app.made.favorite.base.BaseRecyclerViewAdapter
import ebysofyan.app.made.favorite.common.extension.loadWithGlidePlaceholder
import ebysofyan.app.made.favorite.common.extension.toDateFormat
import ebysofyan.app.made.favorite.common.util.Constants
import ebysofyan.app.made.favorite.data.Favorite
import kotlinx.android.synthetic.main.movie_list_item.view.*

/**
 * Created by @ebysofyan on 7/2/19
 */
class FavoriteRecyclerViewAdapter(
    private val onClickListener: (View, Favorite, Int) -> Unit
) : BaseRecyclerViewAdapter<Favorite>() {
    override fun getLayoutResourceId(): Int = R.layout.movie_list_item

    override fun onBindItem(view: View, data: Favorite, position: Int) {
        view.movie_item_image.loadWithGlidePlaceholder(Constants.getImageUrl(fileName = data.posterPath))
        view.movie_item_title.text = data.title
        view.movie_item_rating.text = data.voteAverage.toString()
        view.movie_item_release_date.text = data.releaseDate.toDateFormat()

        view.setOnClickListener {
            onClickListener.invoke(view, data, position)
        }
    }
}