package com.thuypham.ptithcm.baseapp.ui.fragment

import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.customview.ZoomImageView
import com.thuypham.ptithcm.baseapp.databinding.FragmentImageBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baseapp.util.NavConstant

class ImageFragment : BaseFragment<FragmentImageBinding>(R.layout.fragment_image), ZoomImageView.ZoomImageListener {

    private var imageUrl = ""
    override fun setupFirst() {
        arguments?.let {
            imageUrl = it.getString(NavConstant.IMAGE_PATH, "")
        }
    }

    override fun setupView() {
        binding.run {
            loadImage(ivZoomImage, imageUrl)
            ivZoomImage.setImageListener(this@ImageFragment)
        }
    }

    override fun onImageClick() {

    }

    override fun onLongClick() {

    }
}