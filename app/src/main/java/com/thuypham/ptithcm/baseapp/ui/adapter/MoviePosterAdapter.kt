package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemPeopleBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemPosterImageBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter

class MoviePosterAdapter {
    fun initAdapter(glide: RequestManager): BaseViewAdapter<String> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_poster_image
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemPosterImageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder, listItems ->
            },
            bindViewFunc = { binding, item, _ ->
                item as String
                binding as ItemPosterImageBinding
                binding.run {
                    ivPoster.loadImage(glide, item)
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