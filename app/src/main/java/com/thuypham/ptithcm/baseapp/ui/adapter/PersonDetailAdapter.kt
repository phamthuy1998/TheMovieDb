package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemKnowAsBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemPersonImageBinding
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.data.remote.response.Profile
import com.thuypham.ptithcm.data.util.ApiHelper

class PersonDetailAdapter {
    fun setupKnowAsAdapter(): BaseViewAdapter<String> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_know_as
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                ItemKnowAsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            bindViewFunc = { binding, item, position ->
                item as String
                binding as ItemKnowAsBinding
                binding.tvKnowAs.text = item
            },
            diffUtilCallback = {
                BaseItemDiffUtilCallback(
                    areItemsTheSameFunc = { oldItem, newItem -> oldItem == newItem },
                    areContentsTheSameFunc = { oldItem, newItem -> oldItem == newItem }
                )
            }
        )
    }

    fun setupPersonImageAdapter(): BaseViewAdapter<Profile> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_person_image
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                ItemPersonImageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            bindViewFunc = { binding, item, position ->
                item as Profile
                binding as ItemPersonImageBinding
                if (item.filePath.isNullOrEmpty()) {
                    Glide.with(binding.root.context)
                        .load(R.drawable.ic_image_placeholder)
                        .into(binding.ivPerson)
                } else {
                    Glide.with(binding.root.context)
                        .load(ApiHelper.getImagePath(item.filePath!!))
                        .placeholder(R.drawable.ic_image_placeholder)
                        .into(binding.ivPerson)
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