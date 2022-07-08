package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemPeopleBinding
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BasePagedAdapter
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Person
import com.thuypham.ptithcm.data.util.ApiHelper

class PeopleAdapter {
    fun initPeopleAdapter(onPersonItemClick: (position: Int) -> Unit): BasePagedAdapter<Person> {
        return BasePagedAdapter(
            getItemViewTypeFunc = {
                R.layout.item_people
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                ItemPeopleBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onPersonItemClick(viewHolder.absoluteAdapterPosition)
                }
            },
            bindViewFunc = { binding, item, position ->
                item as Person
                binding as ItemPeopleBinding
                binding.run {
                    if (item.profilePath.isNullOrEmpty()) {
                        Glide.with(binding.root.context)
                            .load(R.drawable.ic_image_placeholder)
                            .into(ivAvatar)
                    } else {
                        Glide.with(binding.root.context)
                            .load(ApiHelper.getImagePath(item.profilePath!!))
                            .placeholder(R.drawable.ic_image_placeholder)
                            .into(ivAvatar)
                    }
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