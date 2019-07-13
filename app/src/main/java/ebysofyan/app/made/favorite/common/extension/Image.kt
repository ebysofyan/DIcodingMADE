package ebysofyan.app.made.favorite.common.extension

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ebysofyan.app.made.favorite.R
import java.io.File

/**
 * Created by @ebysofyan on 7/2/19
 */

fun ImageView.loadWithGlidePlaceholder(
    imageUrl: String,
    placeHolderImage: Int = R.drawable.no_image
) {
    val glideOption = RequestOptions().placeholder(placeHolderImage)

    Glide.with(this.context)
        .applyDefaultRequestOptions(glideOption)
        .load(imageUrl)
        .into(this)
}

fun ImageView.loadWithGlidePlaceholder(
    imageUri: Uri?,
    placeHolderImage: Int = R.drawable.no_image
) {
    val glideOption = RequestOptions().placeholder(placeHolderImage)

    Glide.with(this.context)
        .applyDefaultRequestOptions(glideOption)
        .load(imageUri)
        .into(this)
}

fun ImageView.loadWithGlidePlaceholder(
    imageFile: File?,
    placeHolderImage: Int = R.drawable.no_image
) {
    val glideOption = RequestOptions().placeholder(placeHolderImage)

    Glide.with(this.context)
        .applyDefaultRequestOptions(glideOption)
        .load(imageFile)
        .into(this)
}

fun ImageView.loadWithGlidePlaceholder(
    resourceId: Int?,
    placeHolderImage: Int = R.drawable.no_image
) {
    val glideOption = RequestOptions().placeholder(placeHolderImage)

    Glide.with(this.context)
        .applyDefaultRequestOptions(glideOption)
        .load(resourceId)
        .into(this)
}