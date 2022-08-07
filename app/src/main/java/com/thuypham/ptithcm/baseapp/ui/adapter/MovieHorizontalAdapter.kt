package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemKnowAsBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Movie

class MovieHorizontalAdapter {
    fun setupKnowForAdapter(glide: RequestManager, onItemClick: (item: Movie) -> Unit): BaseViewAdapter<Movie> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_movie
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemMovieBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder, listItems ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onItemClick(listItems[viewHolder.absoluteAdapterPosition])
                }
            },
            bindViewFunc = { binding, item, _ ->
                binding as ItemMovieBinding
                item as Movie
                binding.run {
                    tvMovieName.text = item.title?:item.name
                    tvRate.text = item.voteAverage.toString()
                    ivMovie.loadImage(glide, item.posterPath)
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