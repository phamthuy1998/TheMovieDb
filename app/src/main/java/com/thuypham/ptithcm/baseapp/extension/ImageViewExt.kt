package com.thuypham.ptithcm.baseapp.extension

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.data.util.ApiHelper

fun Fragment.loadImage(imageView: ImageView, imagePath: String?, shouldShowDefaultImg: Boolean = true, isYoutubeImage: Boolean = false) {
    imageView.loadImage(Glide.with(this), imagePath, shouldShowDefaultImg)
}


fun ImageView.loadImage(glide: RequestManager, imagePath: String?, shouldShowDefaultImg: Boolean = true, isYoutubeImage: Boolean = false) {
    if (imagePath.isNullOrEmpty()) {
        if (shouldShowDefaultImg)
            glide.load(R.drawable.ic_image_placeholder)
                .centerInside()
                .dontAnimate()
                .into(this)
    } else {
        if (shouldShowDefaultImg)
            glide.load(if (isYoutubeImage) imagePath else ApiHelper.getImagePath(imagePath))
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .format(DecodeFormat.PREFER_RGB_565)
                .dontAnimate()
                .into(this)
        else glide.load(if (isYoutubeImage) imagePath else ApiHelper.getImagePath(imagePath))
            .centerInside()
            .format(DecodeFormat.PREFER_RGB_565)
            .dontAnimate()
            .into(this)
    }
}


fun Fragment.loadImageHighResolution(imageView: ImageView, imagePath: String?, shouldShowDefaultImg: Boolean = true, isYoutubeImage: Boolean = false) {
    if (imagePath.isNullOrEmpty()) {
        if (shouldShowDefaultImg)
            Glide.with(this)
                .load(R.drawable.ic_image_placeholder)
                .centerInside()
                .dontAnimate()
                .into(imageView)
    } else {
        Glide.with(this)
            .load(if (isYoutubeImage) imagePath else ApiHelper.getImagePath(imagePath))
            .placeholder(R.drawable.ic_image_placeholder)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .centerInside()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .dontTransform()
            .dontAnimate()
            .into(imageView)
    }
}

fun ImageView.getBitMap(): Bitmap {
    return (this.drawable as BitmapDrawable).bitmap
}