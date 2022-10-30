package com.thuypham.ptithcm.baseapp.util

import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.extension.milliSecondToDateFormat
import com.thuypham.ptithcm.baselib.base.extension.toMillisecond


@BindingAdapter("app:isEnableMarqueeForever")
fun isEnableMarqueeForever(view: TextView, isEnable: Boolean) {
    if (isEnable) {
        view.apply {
            ellipsize = TextUtils.TruncateAt.MARQUEE
            marqueeRepeatLimit = -1
            isSingleLine = true
            isSelected = true
        }
    }
}

@BindingAdapter("app:dateTimeText")
fun dateTimeText(view: TextView, dateTimeText: String) {
    view.text = dateTimeText.toMillisecond()?.milliSecondToDateFormat()
}

@BindingAdapter("app:linkClickAbleText")
fun linkClickAbleText(textView: TextView, linkClickAbleText: String) {
    textView.text = linkClickAbleText
    TextViewHelper.autoLink(textView, object : TextViewHelper.OnClickListener {
        override fun onLinkClicked(link: String?) {

        }
    })
}

@BindingAdapter("app:maxShowLine")
fun isShowTVShowMore(textView: TextView, maxShowLine: Int) {
    //textView.context.getString(R.string.show_less)
    TextViewHelper.makeTextViewResizable(
        textView, maxShowLine,
        textView.context.getString(R.string.show_more), true
    )
}

@BindingAdapter("selectableBackground")
fun selectableBackground(view: View, selectableBackground: Boolean) {
    if (selectableBackground) {
        view.apply {
            isClickable = true
            isFocusable = true
            with(TypedValue()) {
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
                setBackgroundResource(resourceId)
            }
        }
    }
}


@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, imageUrl: String?) {
    val glide = Glide.with(imageView.context)
    imageView.loadImage(glide, imageUrl, defaultImage = R.drawable.ic_avt)
}

