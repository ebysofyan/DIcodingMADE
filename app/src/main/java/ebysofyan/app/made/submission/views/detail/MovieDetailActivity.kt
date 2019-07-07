package ebysofyan.app.made.submission.views.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.extensions.loadWithGlidePlaceholder
import ebysofyan.app.made.submission.common.extensions.toDateFormat
import ebysofyan.app.made.submission.common.extensions.toast
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.dao.FavoriteDao
import ebysofyan.app.made.submission.data.local.Favorite
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.data.server.TvShow
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 * Created by @ebysofyan on 7/2/19
 */
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var parcelable: Parcelable
    private lateinit var favoriteType: String

    private var hasChange: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestWindowFeature()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_detail)
        setData()

        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(_toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
        _toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        _toolbar.setNavigationOnClickListener {
            navigateUp()
        }
    }

    private fun navigateUp() {
        if (hasChange) setResult(Activity.RESULT_OK, Intent())
        supportFinishAfterTransition()
    }

    private fun setRequestWindowFeature() {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun setData() {
        parcelable = intent.getParcelableExtra(Constants.PARCELABLE_OBJ)

        when (parcelable) {
            is Movie -> {
                val movie = parcelable as Movie
                setDetailAsMovie(movie)
                setFavoriteIcon(movie.id)
            }
            is TvShow -> {
                val tvShow = parcelable as TvShow
                setDetailAsTvShow(tvShow)
                setFavoriteIcon(tvShow.id)
            }
            is Favorite -> {
                val favorite = parcelable as Favorite
                setDetailAsFavorite(favorite)
                setFavoriteIcon(favorite.movieId)
            }
        }
    }

    private fun setFavoriteIcon(id: Long) {
        if (FavoriteDao.isExist(id)) movie_detail_favorite.setImageResource(R.drawable.ic_heart_white_18dp)
        else movie_detail_favorite.setImageResource(R.drawable.ic_heart_outline_white_18dp)
    }

    private fun setDetailAsMovie(movie: Movie) {
        movie_detail_header.loadWithGlidePlaceholder(Constants.getImageUrl("w780", movie.backdropPath))
        movie_detail_poster.loadWithGlidePlaceholder(Constants.getImageUrl(fileName = movie.posterPath))

        movie_detail_title.text = movie.title
        movie_detail_rating.text = movie.voteAverage.toString()
        movie_detail_release_date.text = movie.releaseDate.toDateFormat()
        movie_detail_desc.text = movie.overview

        movie_detail_favorite.setOnClickListener {
            if (FavoriteDao.isExist(movie.id)) {
                Log.e("FavoriteDao.isExist", "true")
                FavoriteDao.deleteFromFavorite(movie.id) {
                    setFavoriteIcon(movie.id)
                    toast(getString(R.string.delete_from_favorite_message))
                }
            } else {
                Log.e("FavoriteDao.isExist", "false")
                val favorite = Favorite(
                    movieId = movie.id,
                    backdropPath = movie.backdropPath,
                    posterPath = movie.posterPath,
                    title = movie.title,
                    voteAverage = movie.voteAverage,
                    releaseDate = movie.releaseDate,
                    overview = movie.overview,
                    type = "movie"
                )
                FavoriteDao.addToFavorite(favorite) {
                    setFavoriteIcon(movie.id)
                    toast(getString(R.string.add_to_favorite_message))
                }
            }

            hasChange = true
        }
    }

    private fun setDetailAsTvShow(tvShow: TvShow) {
        movie_detail_header.loadWithGlidePlaceholder(Constants.getImageUrl("w780", tvShow.backdropPath))
        movie_detail_poster.loadWithGlidePlaceholder(Constants.getImageUrl(fileName = tvShow.posterPath))

        movie_detail_title.text = tvShow.name
        movie_detail_rating.text = tvShow.voteAverage.toString()
        movie_detail_release_date.text = tvShow.firstAirDate.toDateFormat()
        movie_detail_desc.text = tvShow.overview

        movie_detail_favorite.setOnClickListener {
            if (FavoriteDao.isExist(tvShow.id)) {
                FavoriteDao.deleteFromFavorite(tvShow.id) {
                    setFavoriteIcon(tvShow.id)
                    toast(getString(R.string.delete_from_favorite_message))
                }
            } else {
                val favorite = Favorite(
                    movieId = tvShow.id,
                    backdropPath = tvShow.backdropPath,
                    posterPath = tvShow.posterPath,
                    title = tvShow.name,
                    voteAverage = tvShow.voteAverage,
                    releaseDate = tvShow.firstAirDate,
                    overview = tvShow.overview,
                    type = "tv-show"
                )
                FavoriteDao.addToFavorite(favorite) {
                    setFavoriteIcon(tvShow.id)
                    toast(getString(R.string.add_to_favorite_message))
                }
            }

            hasChange = true
        }
    }

    private fun setDetailAsFavorite(fav: Favorite) {
        movie_detail_header.loadWithGlidePlaceholder(Constants.getImageUrl("w780", fav.backdropPath))
        movie_detail_poster.loadWithGlidePlaceholder(Constants.getImageUrl(fileName = fav.posterPath))

        movie_detail_title.text = fav.releaseDate
        movie_detail_rating.text = fav.voteAverage.toString()
        movie_detail_release_date.text = fav.releaseDate.toDateFormat()
        movie_detail_desc.text = fav.overview

        favoriteType = fav.type
        movie_detail_favorite.setOnClickListener {
            if (FavoriteDao.isExist(fav.movieId)) {
                FavoriteDao.deleteFromFavorite(fav.movieId) {
                    setFavoriteIcon(fav.movieId)
                    toast(getString(R.string.delete_from_favorite_message))
                }
            } else {
                val favorite = Favorite(
                    movieId = fav.id,
                    backdropPath = fav.backdropPath,
                    posterPath = fav.posterPath,
                    title = fav.title,
                    voteAverage = fav.voteAverage,
                    releaseDate = fav.releaseDate,
                    overview = fav.overview,
                    type = favoriteType
                )
                FavoriteDao.addToFavorite(favorite) {
                    setFavoriteIcon(fav.movieId)
                    toast(getString(R.string.add_to_favorite_message))
                }
            }

            hasChange = true
        }
    }

    override fun onBackPressed() {
        navigateUp()
    }
}