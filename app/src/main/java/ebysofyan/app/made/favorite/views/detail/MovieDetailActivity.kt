package ebysofyan.app.made.favorite.views.detail

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ebysofyan.app.made.favorite.R
import ebysofyan.app.made.favorite.common.extension.loadWithGlidePlaceholder
import ebysofyan.app.made.favorite.common.extension.toDateFormat
import ebysofyan.app.made.favorite.common.util.Constants
import ebysofyan.app.made.favorite.data.Favorite
import kotlinx.android.synthetic.main.activity_movie_detail.*

/**
 * Created by @ebysofyan on 7/2/19
 */
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var favorite: Favorite
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
            title = favorite.title
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
        favorite = intent.getParcelableExtra(Constants.PARCELABLE_OBJ)
        setDetailAsFavorite(favorite)
    }

    private fun setDetailAsFavorite(fav: Favorite) {
        movie_detail_header.loadWithGlidePlaceholder(Constants.getImageUrl("w780", fav.backdropPath))
        movie_detail_poster.loadWithGlidePlaceholder(Constants.getImageUrl(fileName = fav.posterPath))

        movie_detail_title.text = fav.releaseDate
        movie_detail_rating.text = fav.voteAverage.toString()
        movie_detail_release_date.text = fav.releaseDate.toDateFormat()
        movie_detail_desc.text = fav.overview

        favoriteType = fav.type
    }

    override fun onBackPressed() {
        navigateUp()
    }
}