package com.thuypham.ptithcm.baseapp.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BaseViewAdapter<Model : Any>(
    private val onCreateViewHolderFunc: (viewGroup: ViewGroup, viewType: Int) -> ViewDataBinding,
    private val getItemViewTypeFunc: ((item: Model) -> Int?)? = null,
    private val bindFunc: ((binding: ViewDataBinding, item: Any, position: Int) -> Unit)? = null,
    private val getItemCountFunc: (() -> Int)? = null
) : ListAdapter<Model, RecyclerView.ViewHolder>(DiffCallback<Model>()) {

    override fun getItemViewType(position: Int): Int {
        return getItemViewTypeFunc?.invoke(getItem(position)) ?: super.getItemViewType(position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(onCreateViewHolderFunc.invoke(viewGroup, viewType), bindFunc)
    }

    override fun getItemCount() = currentList.size

    class ItemViewHolder(
        private val mBinding: ViewDataBinding,
        private val bindFunc: ((binding: ViewDataBinding, item: Any, position: Int) -> Unit)? = null,
    ) : RecyclerView.ViewHolder(mBinding.root) {
        fun bindView(item: Any, position: Int) {
            bindFunc?.invoke(mBinding, item, position)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindView(getItem(position), position)
    }

    class DiffCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: T, newItem: T) =
            oldItem.toString() == newItem.toString()
    }

}