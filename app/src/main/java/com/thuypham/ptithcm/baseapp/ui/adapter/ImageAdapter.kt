package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.customview.ZoomImageView
import com.thuypham.ptithcm.baseapp.databinding.ItemImageBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baseapp.extension.loadImageHighResolution
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener

class ImageAdapter {
    fun initImageAdapter(onItemClick: (item: String) -> Unit): BaseViewAdapter<String> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_image
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                ItemImageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder, listItems ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onItemClick(listItems[viewHolder.absoluteAdapterPosition])
                }
            },
            bindViewFunc = { binding, imagePath, position ->
                binding as ItemImageBinding
                imagePath as String

                binding.ivZoomImage.apply {
                    layoutParams.height = (layoutParams.width * 1.5).toInt()
                }
            },
            diffUtilCallback = {
                BaseItemDiffUtilCallback(
                    areItemsTheSameFunc = { oldItem, newItem -> oldItem == newItem },
                    areContentsTheSameFunc = { oldItem, newItem -> oldItem == newItem }
                )
            }
        )
    }
}