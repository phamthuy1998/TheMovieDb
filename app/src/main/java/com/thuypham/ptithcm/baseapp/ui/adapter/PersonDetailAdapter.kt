package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemKnowAsBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemPersonImageBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Profile

class PersonDetailAdapter {
    fun setupKnowAsAdapter(): BaseViewAdapter<String> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_know_as
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemKnowAsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            bindViewFunc = { binding, item, _ ->
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

    fun setupPersonImageAdapter(glide: RequestManager, onItemClick: (imgPath: String) -> Unit): BaseViewAdapter<Profile> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_person_image
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemPersonImageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder, items ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    items[viewHolder.absoluteAdapterPosition].filePath?.let { it1 -> onItemClick(it1) }
                }
            },
            bindViewFunc = { binding, item, _ ->
                item as Profile
                binding as ItemPersonImageBinding
                binding.ivPerson.loadImage(glide, item.filePath)
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