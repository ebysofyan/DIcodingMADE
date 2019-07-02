package ebysofyan.app.made.submission.views.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.extensions.loadWithGlidePlaceholder
import ebysofyan.app.made.submission.common.extensions.toDateFormat
import ebysofyan.app.made.submission.common.extensions.toast
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.data.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*


/**
 * Created by @ebysofyan on 7/2/19
 */
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movie: Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestWindowFeature()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_detail)
        setData()
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
        movie = intent.getParcelableExtra(Constants.MOVIE_OBJ)
        movie_detail_header.loadWithGlidePlaceholder(movie.poster)
        movie_detail_poster.loadWithGlidePlaceholder(movie.poster)

        movie_detail_title.text = movie.title
        movie_detail_rating.text = movie.rating
        movie_detail_release_date.text = movie.releaseDate.toDateFormat("MMMM dd, yyyy")
        movie_detail_desc.text = movie.description

        movie_detail_trailer.setOnClickListener {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(movie.trailer)
            ).apply { putExtra("force_fullscreen", true) }
            try {
                startActivity(webIntent)
            } catch (ex: ActivityNotFoundException) {
                toast("Youtube player not found!")
            }
        }
    }
}