package ebysofyan.app.made.submission.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by @ebysofyan on 7/2/19
 */


@Parcelize
data class Movie(
    val title: String,
    val releaseDate: String,
    val description: String,
    val poster: Int,
    val rating: String,
    val trailer: String
) : Parcelable