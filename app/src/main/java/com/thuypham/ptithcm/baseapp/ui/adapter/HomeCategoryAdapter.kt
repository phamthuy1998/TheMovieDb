package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.base.BaseViewAdapter
import com.thuypham.ptithcm.baseapp.data.remote.response.Movie
import com.thuypham.ptithcm.baseapp.data.remote.response.MovieGenre
import com.thuypham.ptithcm.baseapp.databinding.ItemGenreBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemMovieBinding
import com.thuypham.ptithcm.baseapp.databinding.ItemMoviesCategoryBinding
import com.thuypham.ptithcm.baseapp.model.HomeCategoryData
import com.thuypham.ptithcm.baseapp.model.HomeCategoryType
import com.thuypham.ptithcm.baseapp.model.LoadingItem
import com.thuypham.ptithcm.baselib.base.extension.gone
import com.thuypham.ptithcm.baselib.base.extension.show

class HomeCategoryAdapter {
    fun initAdapter(): BaseViewAdapter<Any> {
        return BaseViewAdapter(
            getItemViewTypeFunc = { item ->
//                item as HomeCategoryData
//                when (item.type) {
//                    HomeCategoryType.MOVIE_GENRES -> R.layout.item_genres_category
//                    else -> R.layout.item_movies_category
//                }
                R.layout.item_movies_category
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), viewType, viewGroup, false)
            },
            bindFunc = { binding, item, position ->
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
                    val rvHeight = when (item.type) {
                        HomeCategoryType.MOVIE_GENRES -> root.context.resources.getDimensionPixelOffset(R.dimen.item_genre_height)
                        else -> root.context.resources.getDimensionPixelOffset(R.dimen.item_rc_movie_height)
                    }
                    val itemAdapter = initItemAdapter()
                    rvItem.apply {
                        adapter = itemAdapter
                        layoutParams.height = rvHeight
                    }
                    itemAdapter.submitList(item.listItems)
                }
            }
        )
    }

    private fun initItemAdapter(): BaseViewAdapter<Any> {
        return BaseViewAdapter(
            getItemViewTypeFunc = { item ->
                when (item) {
                    is Movie -> R.layout.item_movie
                    is MovieGenre -> R.layout.item_genre
                    else -> R.layout.item_loading
                }
            },
            onCreateViewHolderFunc = { viewGroup, viewType ->
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), viewType, viewGroup, false)
            },
            bindFunc = { binding, item, position ->
                when (binding) {
                    is ItemMovieBinding -> {
                        item as Movie
                        binding.run {
                            tvMovieName.text = item.title
                            tvRate.text = item.voteAverage.toString()
                            Glide.with(binding.root.context)
                                .load(item.getImagePath())
                                .into(ivMovie)
                        }
                    }
                    is ItemGenreBinding -> {
                        item as MovieGenre
                        binding.run {
                            tvGenreTitle.text = item.name
                        }

                    }
                }
            }
        )
    }
}