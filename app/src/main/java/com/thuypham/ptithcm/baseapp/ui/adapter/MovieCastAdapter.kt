package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemPeopleBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Person

class MovieCastAdapter {
    fun initPeopleAdapter(glide: RequestManager, onPersonItemClick: (position: Int) -> Unit): BaseViewAdapter<Person> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_people
            },
            onCreateViewHolderFunc = { viewGroup, _ ->
                ItemPeopleBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            },
            addEventListener = { viewHolder, _ ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onPersonItemClick(viewHolder.absoluteAdapterPosition)
                }
            },
            bindViewFunc = { binding, item, _ ->
                logD("bindViewFunc")
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