package com.thuypham.ptithcm.baseapp.util

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.baselib.base.extension.show

class ToolbarHelper constructor(private val view: View) {

    fun setToolbarTitle(title: String, onClick: ((View) -> Unit?)? = null) {
        view.findViewById<AppCompatTextView>(R.id.tvTitle)?.apply {
            show()
            text = title
            isSelected = true
            setOnSingleClickListener { onClick?.invoke(this) }
        }
    }

    fun setToolbarTitle(titleRes: Int, onClick: ((View) -> Unit?)? = null) {
        view.findViewById<AppCompatTextView>(R.id.tvTitle)?.apply {
            show()
            text = view.context.getString(titleRes)
            setOnSingleClickListener { onClick?.invoke(this) }
        }
    }

    fun setLeftBtn(iconResID: Int, onClick: ((View) -> Unit?)? = null) {
        view.findViewById<AppCompatImageView>(R.id.ivLeft)?.apply {
            show()
            setImageResource(iconResID)
            setOnSingleClickListener { onClick?.invoke(this) }
        }
    }

    fun setRightBtn(iconResID: Int, onClick: ((View) -> Unit?)? = null) {
        view.findViewById<AppCompatImageView>(R.id.ivRight)?.apply {
            show()
            setImageResource(iconResID)
            setOnSingleClickListener { onClick?.invoke(this) }
        }
    }

    fun updateRightBtnIconResource(iconResID: Int){
        view.findViewById<AppCompatImageView>(R.id.ivRight)?.apply {
            setImageResource(iconResID)
        }
    }

    fun setSubRightBtn(iconResID: Int, onClick: ((View) -> Unit?)? = null) {
        view.findViewById<AppCompatImageView>(R.id.ivSubRight)?.apply {
            show()
            setImageResource(iconResID)
            setOnSingleClickListener { onClick?.invoke(this) }
        }
    }

    fun setSubRight2Btn(iconResID: Int, onClick: ((View) -> Unit?)? = null) {
        view.findViewById<AppCompatImageView>(R.id.ivSubRight2)?.apply {
            show()
            setImageResource(iconResID)
            setOnSingleClickListener { onClick?.invoke(this) }
        }
    }

}