package com.thuypham.ptithcm.baseapp.ui.fragment

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.PagerSnapHelper
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseFragment
import com.thuypham.ptithcm.baseapp.databinding.FragmentImagesBinding
import com.thuypham.ptithcm.baseapp.ui.adapter.ImageAdapter
import com.thuypham.ptithcm.baseapp.util.NavConstant
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener

class ImagesFragment : BaseFragment<FragmentImagesBinding>(R.layout.fragment_images) {


    private val imageAdapter by lazy { ImageAdapter().initImageAdapter(::onImageClick) }
    private var listImagePath: List<String>? = null

    override fun setupFirst() {
        arguments?.let {
            listImagePath = it.getStringArrayList(NavConstant.IMAGES)
        }
    }

    private fun onImageClick(imagePath: String) {

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupView() {
        toggleControllerVisibility()

        binding.run {
            rvImages.adapter = imageAdapter
            rvImages.setHasFixedSize(true)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(rvImages)

            imageAdapter.submitList(listImagePath)

            this.root.setOnTouchListener { _, event ->
                if(event.action == MotionEvent.ACTION_MOVE){
                    toggleControllerVisibility()
                }
                true
            }
        }
    }



    private fun toggleControllerVisibility() {
        binding.flTopControl.isVisible = true
        binding.flTopControl.postDelayed({
            binding.flTopControl.isVisible = false
        }, 3000)
    }

}