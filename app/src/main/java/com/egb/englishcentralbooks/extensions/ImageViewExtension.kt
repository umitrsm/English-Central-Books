package com.egb.englishcentralbooks.extensions

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


internal enum class ImageScaleType {
    CENTER_CROP, CENTER_INSIDE, CIRCLE_CROP, FIT_CENTER,
}

@SuppressLint("ObsoleteSdkInt")
internal fun ImageView.loadImageUrl(
    imageUrl: String?,
    imageScaleType: ImageScaleType = ImageScaleType.CENTER_INSIDE,
) {
    if (imageUrl == null) return
    var options = RequestOptions()

    options = when (imageScaleType) {
        ImageScaleType.FIT_CENTER -> options.fitCenter()
        ImageScaleType.CENTER_CROP -> options.centerCrop()
        ImageScaleType.CENTER_INSIDE -> options.centerInside()
        ImageScaleType.CIRCLE_CROP -> options.circleCrop()
    }
    Glide.with(this)
        .asBitmap()
        .load(imageUrl)
        .apply(options)
        .thumbnail(0.05f)
        .into(this)
}
