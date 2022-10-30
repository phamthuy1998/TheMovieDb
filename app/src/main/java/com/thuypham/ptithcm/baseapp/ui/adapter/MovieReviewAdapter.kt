package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import com.bumptech.glide.RequestManager
import com.thuypham.ptithcm.baseapp.R
import com.thuypham.ptithcm.baseapp.databinding.ItemReviewBinding
import com.thuypham.ptithcm.baseapp.util.TextViewHelper
import com.thuypham.ptithcm.baselib.base.base.BaseItemDiffUtilCallback
import com.thuypham.ptithcm.baselib.base.base.BasePagedAdapter
import com.thuypham.ptithcm.baselib.base.extension.logD
import com.thuypham.ptithcm.baselib.base.extension.setOnSingleClickListener
import com.thuypham.ptithcm.data.remote.response.Review

class MovieReviewAdapter {
    fun initAdapter(onItemMovieClick: (position: Int) -> Unit): BasePagedAdapter<Review> {
        return BasePagedAdapter(
            onCreateViewHolderFunc = { viewGroup, _ ->
                val inflater = LayoutInflater.from(viewGroup.context)
                ItemReviewBinding.inflate(inflater, viewGroup, false)
            },
            addEventListener = { viewHolder ->
                viewHolder.mBinding.root.setOnSingleClickListener {
                    onItemMovieClick(viewHolder.absoluteAdapterPosition)
                }
            },
            bindViewFunc = { binding, item, position ->
                item as Review
                binding as ItemReviewBinding

                binding.apply {
                    review = item
                    tvContentReview.setOnSingleClickListener { onViewMoreClick(this, item) }
                    tvReadMore.setOnSingleClickListener { onViewMoreClick(this, item) }
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

    fun onViewMoreClick(binding: ItemReviewBinding, review: Review) {
        review.showMore = !review.showMore
        binding.run {
            if (review.showMore) {
                tvContentReview.maxLines = 7
                tvReadMore.text = tvReadMore.context.getString(R.string.show_more)
            } else {
                tvContentReview.maxLines = tvContentReview.text.length
                tvReadMore.text = tvReadMore.context.getString(R.string.show_less)
            }
        }
    }
}