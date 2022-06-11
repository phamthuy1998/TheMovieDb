package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.View
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemMoviesCategoryBinding
import com.thuypham.ptithcm.baseapp.model.LoadingItem
import com.thuypham.ptithcm.baselib.base.extension.gone
import com.thuypham.ptithcm.baselib.base.extension.hide
import com.thuypham.ptithcm.baselib.base.extension.show
import com.thuypham.ptithcm.baseapp.model.HomeCategoryData

class MovieListItemViewHolder(private val binding: ItemMoviesCategoryBinding) : BaseViewHolder<HomeCategoryData>(binding.root) {

    private val movieAdapter = RecyclerViewMultiTypeAdapter()
            .registerViewHolderFactory(MovieItemViewHolderFactory())
            .registerViewHolderFactory(GenreItemViewHolderFactory())
            .registerViewHolderFactory(LoadingItemViewHolderFactory())

    override fun bind(item: HomeCategoryData) {
        binding.apply {
            tvTitle.text = item.title
            when {
                item.listItems.isNullOrEmpty() -> {
                    layoutLoading.hide()
                    viewStubEmpty.viewStub?.show()
                }
                item.listItems?.first() is LoadingItem -> {
                    layoutLoading.show()
                    viewStubEmpty.viewStub?.gone()
                }
                else -> {
                    layoutLoading.gone()
                    viewStubEmpty.viewStub?.gone()
                }
            }

            rvItem.adapter = movieAdapter
            movieAdapter.submitList(item.listItems)
        }
    }
}

class MovieListItemsViewHolderFactory : ViewHolderFactory {
    override fun layoutId(): Int {
        return R.layout.item_movies_category
    }

    override fun createViewHolder(viewItem: View): BaseViewHolder<*> {
        return MovieListItemViewHolder(ItemMoviesCategoryBinding.bind(viewItem))
    }

    override fun bindViewHolder(viewHolder: BaseViewHolder<*>, itemModel: ItemModel) {
        (viewHolder as MovieListItemViewHolder).bind(itemModel as HomeCategoryData)
    }

}