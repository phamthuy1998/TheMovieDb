package com.thuypham.ptithcm.baseapp.ui.dialog

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.customview.ZoomImageView
import com.thuypham.ptithcm.baseapp.databinding.FragmentDetailImageBinding
import com.thuypham.ptithcm.baseapp.extension.getBitMap
import com.thuypham.ptithcm.baseapp.extension.loadImageHighResolution
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baselib.base.base.BaseDialogFragment
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.baselib.base.extension.shareImageFromUrlToOtherApp

class ImageDetailDialogFragment(

) : BaseDialogFragment<FragmentDetailImageBinding>(R.layout.fragment_detail_image),
    ZoomImageView.ZoomImageListener {

    companion object {
        val TAG = ImageDetailDialogFragment::class.java.simpleName
    }

    override val isFullScreen = true
    private var imagePath: String = ""
    private var bitmap: Bitmap? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setStyle(STYLE_NO_FRAME, 0)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return dialog
    }


    override fun setupView() {
        imagePath = arguments?.getString(NavConstant.IMAGE_PATH, "") ?: ""
        binding.apply {
            ivZoomImage.loadImageHighResolution(imagePath)
            ivZoomImage.setImageListener(this@ImageDetailDialogFragment)
            ivClose.setOnSingleClickListener {
                dismiss()
            }
            ivMenu.setOnSingleClickListener {
                showImagePopupMenu(it)
            }
            toggleControllerVisibility()
        }
    }

    override fun onImageClick() {
        toggleControllerVisibility()
    }

    private fun toggleControllerVisibility() {
        binding.flTopControl.isVisible = true
        binding.flTopControl.postDelayed({
            binding.flTopControl.isVisible = false
        }, 3000)
    }

    private fun showImagePopupMenu(view: View) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.image_menu, menu)
            setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.MenuShareImg -> {
                        shareImage()
                    }
                    R.id.menuSaveImg -> {

                    }
                }
                true
            })
            show()
        }
    }

    private fun shareImage() {
        val bitmap = binding.ivZoomImage.getBitMap()
        shareImageFromUrlToOtherApp(bitmap, imagePath)
    }


    override fun onLongClick() {

    }
}