package com.thuypham.ptithcm.baselib.base.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class BasePagedAdapter<T : Any>(
    private var onCreateViewHolderFunc: (viewGroup: ViewGroup, viewType: Int) -> ViewDataBinding,
    diffUtilCallback: () -> BaseItemDiffUtilCallback<T>,
    private var getItemViewTypeFunc: ((item: T) -> Int?)? = null,
    var bindViewFunc: ((binding: ViewDataBinding, item: Any, position: Int) -> Unit)? = null,
    private var addEventListener: ((viewHolder: ItemViewHolder) -> Unit)? = null
) : PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback(diffUtilCallback.invoke())) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.let { getItemViewTypeFunc?.invoke(it) } ?: super.getItemViewType(position)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(onCreateViewHolderFunc(viewGroup, viewType), bindViewFunc).apply {
            addEventListener?.invoke(this)
        }
    }

    class ItemViewHolder(
        val mBinding: ViewDataBinding,
        private val bindFunc: ((binding: ViewDataBinding, item: Any, position: Int) -> Unit)? = null,
    ) : RecyclerView.ViewHolder(mBinding.root) {
        fun bindView(item: Any, position: Int) {
            bindFunc?.invoke(mBinding, item, position)
        }

    }

    fun getItemAtPosition(position: Int): T? {
        return getItem(position)
    }

    fun notifyDataAtPos(position: Int) {
        notifyItemChanged(position)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as ItemViewHolder).bindView(it, position) }
    }

}

fun <T : Any> diffCallback(baseDiffUtilCallback: BaseItemDiffUtilCallback<T>): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            baseDiffUtilCallback.areItemsTheSameFunc.invoke(oldItem as? T, newItem as? T)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            baseDiffUtilCallback.areContentsTheSameFunc.invoke(oldItem as? T, newItem as? T)
    }
}

data class BaseItemDiffUtilCallback<T>(
    var areItemsTheSameFunc: ((T?, T?) -> Boolean),
    var areContentsTheSameFunc: ((T?, T?) -> Boolean)
)
