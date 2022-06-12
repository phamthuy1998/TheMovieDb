package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseViewAdapter
import com.thuypham.ptithcm.baseapp.databinding.ItemLoadingBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieGridviewBinding
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.util.ApiHelper

class MovieAdapter {
    fun initMovieAdapter(): BaseViewAdapter<Any> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                when (it) {
                    is Movie -> R.layout.item_movie_gridview
                    else -> R.layout.item_loading
                }
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), viewType, viewGroup, false)
            },
            bindViewFunc = { binding, item, position ->
                when (item) {
                    is Movie -> {
                        (binding as? ItemMovieGridviewBinding)?.run {
                            tvMovieName.text = item.title
                            tvRate.text = item.voteAverage.toString()
                            Glide.with(binding.root.context)
                                .load(ApiHelper.getImagePath(item.posterPath ?: ""))
                                .placeholder(R.drawable.ic_image_placeholder)
                                .into(ivMovie)
                        }
                    }
                    else -> {
                        (binding as? ItemLoadingBinding)?.run {
                            layoutLoading.layoutParams.height = binding.root.context.resources.getDimensionPixelSize(R.dimen.grid_item_loading_height)
                        }
                    }
                }
            }

        )
    }
}