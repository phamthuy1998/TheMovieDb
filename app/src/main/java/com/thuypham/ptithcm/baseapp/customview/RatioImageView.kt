package com.thuypham.ptithcm.baseapp.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.makeramen.roundedimageview.RoundedImageView
import com.thuypham.ptithcm.baseapp.R

class RatioImageView constructor(context: Context, private val attrs: AttributeSet) : RoundedImageView(context, attrs) {

    init {
        setLayerType(View.LAYER_TYPE_NONE, null)
    }

    @SuppressLint("Recycle")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val values = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
        val dimensionRatio = values.getString(R.styleable.RatioImageView_dimenRatio) ?: ""

        if (dimensionRatio.isNotEmpty()) {
            val temp = dimensionRatio.split(":")
            val widthImgRatio = (temp.firstOrNull() ?: "0").toFloat()
            val heightImgRatio = (temp.lastOrNull() ?: "0").toFloat()

            val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
            val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)

            if (widthImgRatio > 0f && heightImgRatio > 0f) {
                if (measuredWidth > 0) {
                    val heightMs = MeasureSpec.makeMeasureSpec((measuredWidth * heightImgRatio / widthImgRatio).toInt(), MeasureSpec.AT_MOST)
                    setMeasuredDimension(widthMeasureSpec, heightMs)
                } else if (measuredHeight > 0) {
                    val widthMs = MeasureSpec.makeMeasureSpec((measuredHeight * widthImgRatio / heightImgRatio).toInt(), MeasureSpec.AT_MOST)
                    setMeasuredDimension(widthMs, heightMeasureSpec)
                } else {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                }
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun hasOverlappingRendering(): Boolean {
        return false
    }
}