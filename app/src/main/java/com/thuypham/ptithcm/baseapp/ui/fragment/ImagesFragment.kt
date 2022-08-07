package com.thuypham.ptithcm.baseapp.ui.fragment

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentImagesBinding
import com.thuypham.ptithcm.baseapp.extension.getBitMap
import com.thuypham.ptithcm.baseapp.ui.adapter.ImageAdapter
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.baselib.base.extension.shareImageFromUrlToOtherApp

class ImagesFragment : BaseFragment<FragmentImagesBinding>(R.layout.fragment_images) {


    private val imageAdapter by lazy { ImageAdapter().initImageAdapter(::onImageClick, Glide.with(this)) }
    private var listImagePath: List<String>? = null

    override fun setupFirst() {
        arguments?.let {
            listImagePath = it.getStringArrayList(NavConstant.IMAGES)
        }
    }

    private fun onImageClick(url: String) {
        toggleControllerVisibility()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupView() {
        toggleControllerVisibility()

        binding.run {
            rvImages.layoutManager = LinearLayoutManager(requireContext() ,LinearLayoutManager.HORIZONTAL, false)
            rvImages.setHasFixedSize(true)
            rvImages.adapter = imageAdapter
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(rvImages)

            imageAdapter.submitList(listImagePath)

//            ivMenu.setOnSingleClickListener {
//                showImagePopupMenu(it)
//            }


        }
    }

    private fun showImagePopupMenu(view: View) {
        PopupMenu(requireContext(), view).apply {
            menuInflater.inflate(R.menu.image_menu, menu)
            this.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.MenuShareImg -> {
                        shareImage()
                    }
                    R.id.menuSaveImg -> {

                    }
                }
                true
            }
            show()
        }
    }

    private fun shareImage() {
//        val bitmap = binding.ivZoomImage.getBitMap()
//        shareImageFromUrlToOtherApp(bitmap, imagePath)
    }


    private fun toggleControllerVisibility() {
//        binding.flTopControl.isVisible = true
//        binding.flTopControl.postDelayed({
//            binding.flTopControl.isVisible = false
//        }, 3000)
    }

    override fun clearData() {
        super.clearData()
        binding.rvImages.adapter = null
    }


}