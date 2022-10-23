package com.thuypham.ptithcm.baselib.base.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BaseViewAdapter<T : Any>(
    private val onCreateViewHolderFunc: (viewGroup: ViewGroup, viewType: Int) -> ViewDataBinding,
    private val diffUtilCallback: () -> BaseItemDiffUtilCallback<T>,
    private val getItemViewTypeFunc: ((item: T) -> Int?)? = null,
    private val bindViewFunc: ((binding: ViewDataBinding, item: Any, position: Int) -> Unit)? = null,
    private val addEventListener: ((viewHolder: ItemViewHolder, listItems: List<T>) -> Unit)? = null
) : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback(diffUtilCallback.invoke())) {

    override fun getItemViewType(position: Int): Int {
        return getItemViewTypeFunc?.invoke(getItem(position)) ?: super.getItemViewType(position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(onCreateViewHolderFunc(viewGroup, viewType), bindViewFunc).apply {
            addEventListener?.invoke(this, currentList)
        }
    }

    fun getItemAtPosition(position: Int): T? {
        return getItem(position)
    }

    class ItemViewHolder(
        val mBinding: ViewDataBinding,
        private val bindFunc: ((binding: ViewDataBinding, item: Any, position: Int) -> Unit)? = null,
    ) : RecyclerView.ViewHolder(mBinding.root) {
        fun bindView(item: Any, position: Int) {
            bindFunc?.invoke(mBinding, item, position)
        }
    }

    fun getItemAtPos(position: Int): T {
        return currentList.get(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindView(getItem(position), position)
    }
/*

    class DiffCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) =
            oldItem.toString() == newItem.toString()

        override fun areContentsTheSame(oldItem: T, newItem: T) =
            oldItem.toString() == newItem.toString()
    }
*/

}