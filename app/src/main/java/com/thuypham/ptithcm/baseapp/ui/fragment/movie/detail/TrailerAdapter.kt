package com.thuypham.ptithcm.baseapp.ui.fragment.movie.detail

import android.view.LayoutInflater
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemTrailerBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Video
import kotlin.math.log

class TrailerAdapter {
    fun setupAdapter(glide: RequestManager, onChildItemClick: ((item: Video) -> Unit)): BaseViewAdapter<Video> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_trailer
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemTrailerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder, items ->
                viewHolder.mBinding.apply {
                    root.setOnSingleClickListener { onChildItemClick.invoke(items[viewHolder.absoluteAdapterPosition]) }
                }
            },
            bindViewFunc = { binding, item, _ ->
                item as Video
                binding as ItemTrailerBinding
                logD("bindviewholder: ${item.getImagePath()} - ${item.name}")
                binding.tvTrailerName.text = item.name
                binding.ivTrailer.loadImage(glide, item.getImagePath(), isYoutubeImage = true)
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