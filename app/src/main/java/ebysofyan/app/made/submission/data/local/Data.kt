package ebysofyan.app.made.submission.data.local

import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.android.parcel.Parcelize

/**
 * Created by @ebysofyan on 7/8/19
 */

@Parcelize
@Entity
data class Favorite(
    @Id var id: Long = 0,
    var movieId: Long = 0,
    val adult: Boolean = false,
    val backdropPath: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String,
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double,
    val voteCount: Int = 0,
    val originalName: String = "",
    val type: String
) : Parcelable


@Parcelize
data class NotificationData(
    val notificationId: Int = 101,
    val title: String = "",
    val body: String = ""
) : Parcelable