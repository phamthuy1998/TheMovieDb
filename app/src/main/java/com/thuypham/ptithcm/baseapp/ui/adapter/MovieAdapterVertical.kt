package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieVerticalBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BasePagedAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Movie

class MovieAdapterVertical {
    fun initMovieAdapter(onItemMovieClick: (position: Int) -> Unit): BasePagedAdapter<Movie> {
        return BasePagedAdapter(
            getItemViewTypeFunc = {
                R.layout.item_movie_vertical
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), viewType, viewGroup, false)
            },
            addEventListener = { viewHolder ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onItemMovieClick(viewHolder.absoluteAdapterPosition)
                }
            },
            bindViewFunc = { binding, item, position ->
                item as Movie
                when (binding) {
                    is ItemMovieVerticalBinding -> {
                        binding.run {
                            tvMovieName.text = item.title
                            tvDescription.text = item.overview
                            tvRate.text = item.voteAverage.toString()
                            ivMovie.loadImage(item.posterPath)
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