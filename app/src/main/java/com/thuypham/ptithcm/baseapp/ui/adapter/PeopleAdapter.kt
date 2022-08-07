package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemPeopleBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BasePagedAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Person
import com.thuypham.ptithcm.data.util.ApiHelper

class PeopleAdapter {
    fun initPeopleAdapter(glide: RequestManager, onPersonItemClick: (position: Int) -> Unit): BasePagedAdapter<Person> {
        return BasePagedAdapter(
            getItemViewTypeFunc = {
                R.layout.item_people
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemPeopleBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onPersonItemClick(viewHolder.absoluteAdapterPosition)
                }
            },
            bindViewFunc = { binding, item, _ ->
                item as Person
                binding as ItemPeopleBinding
                binding.run {
                    ivAvatar.loadImage(glide, item.profilePath)
                    tvPersonName.text = item.name
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