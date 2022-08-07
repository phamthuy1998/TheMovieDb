package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemGenreBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemMoviesCategoryBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemPopularPersonBinding
import com.thuypham.ptithcm.baseapp.extension.loadImage
import com.thuypham.ptithcm.baseapp.model.HomeCategoryData
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baseapp.model.LoadingItem
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BaseViewAdapter
import com.thuypham.ptithcm.baselib.base.extension.gone
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.baselib.base.extension.show
import com.thuypham.ptithcm.data.remote.response.Movie
import com.thuypham.ptithcm.data.remote.response.MovieGenre
import com.thuypham.ptithcm.data.remote.response.Person

class HomeCategoryAdapter {
    fun initHomeCategoryAdapter(
        onCategoryItemClick: (item: Any) -> Unit,
        onChildItemClick: (item: Any) -> Unit,
        glide: RequestManager
    ): BaseViewAdapter<Any> {
        return BaseViewAdapter(
            getItemViewTypeFunc = {
                R.layout.item_movies_category
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(viewGroup.context), viewType, viewGroup, false)

                binding
            },
            addEventListener = { viewHolder, listItems ->
                (viewHolder.mBinding as ItemMoviesCategoryBinding).run {
                    ivViewAll.setOnSingleClickListener {
                        onCategoryItemClick(listItems[viewHolder.absoluteAdapterPosition])
                    }
                    tvTitle.setOnSingleClickListener {
                        onCategoryItemClick(listItems[viewHolder.absoluteAdapterPosition])
                    }
                }
            },
            bindViewFunc = { binding, item, _ ->
                item as HomeCategoryData
                binding as ItemMoviesCategoryBinding
                binding.run {
                    tvTitle.text = item.title
                    when {
                        item.listItems.isNullOrEmpty() -> {
                            viewStubEmpty.viewStub?.show()
                        }
                        item.listItems?.first() is LoadingItem -> {
                            viewStubEmpty.viewStub?.gone()
                        }
                        else -> {
                            viewStubEmpty.viewStub?.gone()
                        }
                    }

                    // Calculate item height
                    val resource = root.context.resources
                    val rvHeight = when (item.type) {
                        HomeCategoryType.MOVIE_GENRES -> resource.getDimensionPixelOffset(R.dimen.item_genre_height)
                        HomeCategoryType.POPULAR_PEOPLE -> root.context.resources.getDimensionPixelOffset(R.dimen.item_rc_popular_height)
                        else -> root.context.resources.getDimensionPixelOffset(R.dimen.item_rc_movie_height)
                    }

                    val itemHeight = rvHeight + resource.getDimensionPixelSize(R.dimen.item_title_category_height)
                    rootLayoutCategoryItem.layoutParams.height = itemHeight

                    // setup recyclerview
                    val itemAdapter = initListItemAdapter(glide, onChildItemClick)
                    rvItem.apply {
                        adapter = itemAdapter
                        layoutParams.height = rvHeight
                    }
                    itemAdapter.submitList(item.listItems)
                }
            },
            diffUtilCallback = {
                BaseItemDiffUtilCallback(
                    areItemsTheSameFunc = { oldItem, newItem -> oldItem?.toString() == newItem?.toString() },
                    areContentsTheSameFunc = { oldItem, newItem -> oldItem?.toString() == newItem?.toString() }
                )

            }
        )
    }

    private fun initListItemAdapter(glide: RequestManager, onChildItemClick: ((item: Any) -> Unit)): BaseViewAdapter<Any> {
        return BaseViewAdapter(
            getItemViewTypeFunc = { item ->
                when (item) {
                    is Movie -> R.layout.item_movie
                    is MovieGenre -> R.layout.item_genre
                    is Person -> R.layout.item_popular_person
                    else -> R.layout.item_loading
                }
            },
            addEventListener = { viewHolder, items ->
                viewHolder.mBinding.apply {
                    root.setOnSingleClickListener { onChildItemClick.invoke(items[viewHolder.absoluteAdapterPosition]) }
                }
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), viewType, viewGroup, false)
            },
            bindViewFunc = { binding, item, _ ->
                when (binding) {
                    is ItemMovieBinding -> {
                        item as Movie
                        binding.run {
                            tvMovieName.text = item.title
                            tvRate.text = item.voteAverage.toString()
                            ivMovie.loadImage(glide, item.posterPath)
                        }
                    }
                    is ItemGenreBinding -> {
                        item as MovieGenre
                        binding.run {
                            tvGenreTitle.text = item.name
                        }

                    }
                    is ItemPopularPersonBinding -> {
                        item as Person
                        binding.run {
                            tvPersonName.text = item.name
                            ivAvt.loadImage(glide, item.profilePath)
                        }

                    }
                }
            },
            diffUtilCallback = {
                BaseItemDiffUtilCallback(
                    areItemsTheSameFunc = { oldItem, newItem -> oldItem?.toString() == newItem?.toString() },
                    areContentsTheSameFunc = { oldItem, newItem -> oldItem?.toString() == newItem?.toString() }
                )

            }
        )
    }
}