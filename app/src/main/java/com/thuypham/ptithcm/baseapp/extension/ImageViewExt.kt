package com.thuypham.ptithcm.baseapp.extension

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.bumptech.glide.request.transition.Transition
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.data.util.ApiHelper

fun ImageView.loadImage(imagePath: String?, shouldShowDefaultImg: Boolean = true) {
    if (imagePath.isNullOrEmpty()) {
        if (shouldShowDefaultImg)
            Glide.with(this)
                .load(R.drawable.ic_image_placeholder)
                .centerInside()
                .into(this)
    } else {
        Glide.with(this)
            .load(ApiHelper.getImagePath(ApiHelper.getImagePath(imagePath)))
            .centerInside()
            .placeholder(R.drawable.ic_image_placeholder)
            .format(DecodeFormat.PREFER_RGB_565)
            .into(this)
    }
}


fun ImageView.loadImageHighResolution(imagePath: String?, shouldShowDefaultImg: Boolean = true) {
    val imageView = this
    if (imagePath.isNullOrEmpty()) {
        if (shouldShowDefaultImg)
            Glide.with(imageView)
                .load(R.drawable.ic_image_placeholder)
                .centerInside()
                .into(imageView)
    } else {
        Glide.with(this)
            .load(ApiHelper.getImagePath(ApiHelper.getImagePath(imagePath)))
            .placeholder(R.drawable.ic_image_placeholder)
            .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            .centerInside()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .dontTransform()
            .into(imageView)
    }
}

fun ImageView.getBitMap(): Bitmap {
    return (this.drawable as BitmapDrawable).bitmap
}