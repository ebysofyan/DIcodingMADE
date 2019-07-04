package ebysofyan.app.made.submission.views.list.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.data.BaseResponse
import ebysofyan.app.made.submission.data.TvShow
import ebysofyan.app.made.submission.repository.MovieRepository
import ebysofyan.app.made.submission.views.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import java.util.*

class TvShowListFragment : Fragment(), TvShowListContract.View {

    private lateinit var adapter: TvShowRecyclerViewAdapter
    private lateinit var presenter: TvShowListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        if (savedInstanceState != null) {
            adapter.clear()
            adapter.addItems(savedInstanceState.getParcelableArrayList(Constants.TV_SHOWS))
        }

        initPresenter()

        movie_swipe_refresh?.setOnRefreshListener {
            presenter.fetchTvShows()
        }
    }

    private fun initPresenter() {
        presenter = TvShowListPresenter(context, MovieRepository())
        presenter.attach(this)
        presenter.fetchTvShows()
    }

    private fun initRecyclerView() {
        adapter = TvShowRecyclerViewAdapter { view, movie, _ ->
            val intent = Intent(context, MovieDetailActivity::class.java)
            val bundle = Bundle().apply {
                putParcelable(Constants.PARCELABLE_OBJ, movie)
            }
            intent.putExtras(bundle)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!,
                view.movie_item_image,
                "image_poster"
            )
            startActivity(intent, options.toBundle())
        }
        movie_recycler_view.layoutManager = GridLayoutManager(context, 3)
        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.adapter = adapter
    }

    override fun onLoading(show: Boolean) {
        movie_swipe_refresh?.isRefreshing = show
    }

    override fun onTvShowLoaded(tvShow: BaseResponse<TvShow>?) {
        tvShow?.results?.let {
            adapter.clear()
            adapter.addItems(it)
        }
    }

    fun refreshItem() {
        presenter.fetchTvShows()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Constants.TV_SHOWS, ArrayList(adapter.getItems()))
    }
}
