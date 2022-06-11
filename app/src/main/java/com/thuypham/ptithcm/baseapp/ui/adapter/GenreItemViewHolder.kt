package com.thuypham.ptithcm.baseapp.ui.adapter


import android.view.View
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.data.remote.response.MovieGenre
import com.thuypham.ptithcm.baseapp.databinding.ItemGenreBinding

class GenreItemViewHolder(private val binding: ItemGenreBinding) : BaseViewHolder<MovieGenre>(binding.root) {


    override fun bind(item: MovieGenre) {
        binding.apply {
            tvGenreTitle.text = item.name
            tvGenreTitle.addOnItemClick()
        }
    }
}

class GenreItemViewHolderFactory : ViewHolderFactory {
    override fun layoutId(): Int {
        return R.layout.item_genre
    }

    override fun createViewHolder(viewItem: View): BaseViewHolder<*> {
        return GenreItemViewHolder(ItemGenreBinding.bind(viewItem))
    }

    override fun bindViewHolder(viewHolder: BaseViewHolder<*>, itemModel: ItemModel) {
        (viewHolder as GenreItemViewHolder).bind(itemModel as MovieGenre)
    }
}