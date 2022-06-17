package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieVerticalBinding
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BasePagedAdapter
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.util.ApiHelper

class MovieAdapterVertical {
    fun initMovieAdapter(): BasePagedAdapter<Movie> {
        return BasePagedAdapter(
            getItemViewTypeFunc = {
                R.layout.item_movie_vertical
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), viewType, viewGroup, false)
            },
            bindViewFunc = { binding, item, position ->
                item as Movie
                when (binding) {
                    is ItemMovieVerticalBinding -> {
                        binding.run {
                            tvMovieName.text = item.title
                            tvDescription.text = item.overview
                            tvRate.text = item.voteAverage.toString()
                            Glide.with(binding.root.context)
                                .load(ApiHelper.getImagePath(item.posterPath ?: ""))
                                .placeholder(R.drawable.ic_image_placeholder)
                                .into(ivMovie)
                        }
                    }
                }
            },
            diffUtilCallback = {
                BaseItemDiffUtilCallback(
                    areItemsTheSameFunc = { oldItem, newItem -> oldItem?.id == newItem?.id },
                    areContentsTheSameFunc = { oldItem, newItem -> oldItem == newItem }
                )
            }

        )
    }
}