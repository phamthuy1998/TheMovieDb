package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemGenreVerticalBinding
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.MovieGenre

class GenreAdapter {

    fun initGenreAdapter(onItemClick: (item: MovieGenre) -> Unit): BaseViewAdapter<MovieGenre> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_genre_vertical
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemGenreVerticalBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder, listItems ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onItemClick(listItems[viewHolder.absoluteAdapterPosition])
                }
            },
            bindViewFunc = { binding, item, _ ->
                binding as ItemGenreVerticalBinding
                item as MovieGenre

                binding.tvGenreTitle.text = item.name
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