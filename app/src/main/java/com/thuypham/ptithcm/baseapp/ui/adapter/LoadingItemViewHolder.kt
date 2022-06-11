package com.thuypham.ptithcm.baseapp.ui.adapter


import android.view.View
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.data.remote.response.MovieGenre
import com.thuypham.ptithcm.baseapp.databinding.ItemGenreBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemLoadingBinding
import com.thuypham.ptithcm.baseapp.model.LoadingItem

class LoadingItemViewHolder(private val binding: ItemLoadingBinding) : BaseViewHolder<LoadingItem>(binding.root) {

    override fun bind(item: LoadingItem) {

    }
}

class LoadingItemViewHolderFactory : ViewHolderFactory {
    override fun layoutId(): Int {
        return R.layout.item_loading
    }

    override fun createViewHolder(viewItem: View): BaseViewHolder<*> {
        return LoadingItemViewHolder(ItemLoadingBinding.bind(viewItem))
    }

    override fun bindViewHolder(viewHolder: BaseViewHolder<*>, itemModel: ItemModel) {
        (viewHolder as LoadingItemViewHolder).bind(itemModel as LoadingItem)
    }
}