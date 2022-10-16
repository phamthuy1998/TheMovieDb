package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemGenreBinding
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter

class MovieGenreAdapter {
    fun setupMovieGenreAdapter(): BaseViewAdapter<String> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_genre
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemGenreBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            bindViewFunc = { binding, item, _ ->
                item as String
                binding as ItemGenreBinding
                binding.tvGenreTitle.text = item
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