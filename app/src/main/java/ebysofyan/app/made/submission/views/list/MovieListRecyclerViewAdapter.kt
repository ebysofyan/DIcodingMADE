package ebysofyan.app.made.submission.views.list

import android.view.View
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.base.BaseRecyclerViewAdapter
import ebysofyan.app.made.submission.common.extensions.loadWithGlidePlaceholder
import ebysofyan.app.made.submission.common.extensions.toDateFormat
import ebysofyan.app.made.submission.data.Movie
import kotlinx.android.synthetic.main.movie_list_item.view.*

/**
 * Created by @ebysofyan on 7/2/19
 */
class MovieListRecyclerViewAdapter(
    private val onClickListener: (View, Movie, Int) -> Unit
) : BaseRecyclerViewAdapter<Movie>() {
    override fun getLayoutResourceId(): Int = R.layout.movie_list_item

    override fun onBindItem(view: View, data: Movie, position: Int) {
        view.movie_item_image.loadWithGlidePlaceholder(data.poster)
        view.movie_item_title.text = data.title
        view.movie_item_rating.text = data.rating
        view.movie_item_release_date.text = data.releaseDate.toDateFormat()

        view.setOnClickListener {
            onClickListener.invoke(view, data, position)
        }
    }
}