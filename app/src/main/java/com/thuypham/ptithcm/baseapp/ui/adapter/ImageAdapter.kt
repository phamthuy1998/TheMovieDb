package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemImageBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.util.ApiHelper

class ImageAdapter {
    fun initImageAdapter(onItemClick: (item: String) -> Unit, glide: RequestManager): BaseViewAdapter<String> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_image
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                val inflater = LayoutInflater.from(viewGroup.context)
                ItemImageBinding.inflate(inflater, viewGroup, false)
            },
            addEventListener = { viewHolder, listItems ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onItemClick(listItems[viewHolder.absoluteAdapterPosition])
                }
            },
            bindViewFunc = { binding, imagePath, _ ->
                binding as ItemImageBinding
                imagePath as String

                binding.ivZoomImage.loadImage(glide, imagePath)
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