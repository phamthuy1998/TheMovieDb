package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thuypham.ptithcm.baseapp.databinding.ItemPagingStateBinding

class PagingStateAdapter(val retry: () -> Unit) :
    LoadStateAdapter<PagingStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = ItemPagingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding).apply {
            binding.retryButton.setOnClickListener { retry() }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(private val binding: ItemPagingStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) = binding.apply {
            when (loadState) {
                LoadState.Loading -> {
                    progressBar.isVisible = true
                    retryButton.isVisible = false
                }
                // is LoadState.NotLoading -> TODO()
                is LoadState.Error -> {
                    progressBar.isVisible = false
                    retryButton.isVisible = true
                }
            }
        }
    }
}
