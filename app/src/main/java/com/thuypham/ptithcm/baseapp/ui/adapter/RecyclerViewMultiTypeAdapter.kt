package com.thuypham.ptithcm.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


interface ItemModel {
    @LayoutRes
    fun layoutId(): Int

    fun id(): Long = hashCode().toLong()

    fun contentsAreTheSame(newItem: ItemModel): Boolean = toString() == newItem.toString()
}

@Suppress("UNCHECKED_CAST")
abstract class BaseViewHolder<T : ItemModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var onItemClick: ((view: View, item: T) -> Unit)? = null

    protected fun View.addOnItemClick(localItemClick: ((view: View, item: T) -> T)? = null) {
        setOnClickListener {
            if (itemView.tag is ItemModel) {
                onItemClick?.invoke(it, (localItemClick?.invoke(it, getItemModel()) ?: getItemModel())) ?: localItemClick?.invoke(it, getItemModel())
            }
        }
    }

    protected fun RecyclerViewMultiTypeAdapter.addOnNestedItemClick(onItemCLick: ((view: View, itemModel: ItemModel) -> T?)? = null) {
        setOnItemClick { view, item->
            if (itemView.tag is ItemModel) {
                onItemCLick?.invoke(view, (onItemCLick.invoke(view, getItemModel()) ?: item)) ?: onItemCLick?.invoke(view, getItemModel())
            }
        }
    }

    fun getItemModel(): T = itemView.tag as T

    fun getString(resId: Int) = itemView.context.getString(resId)

    abstract fun bind(item: T)
}

interface ViewHolderFactory {
    @LayoutRes
    fun layoutId(): Int
    fun createViewHolder(viewItem: View): BaseViewHolder<*>
    fun bindViewHolder(viewHolder: BaseViewHolder<*>, itemModel: ItemModel)
}

class DiffItemModel : DiffUtil.ItemCallback<ItemModel>() {
    override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel) =
        oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel) =
        oldItem.contentsAreTheSame(newItem)
}

class RecyclerViewMultiTypeAdapter : ListAdapter<ItemModel, BaseViewHolder<*>>(AsyncDifferConfig.Builder(DiffItemModel()).build()) {

    init {
        setHasStableIds(true)
    }

    private val factories: MutableList<ViewHolderFactory> = mutableListOf()

    private var onItemClickListener: ((view: View, itemModel: ItemModel) -> Unit)? = null

    fun registerViewHolderFactory(viewHolderFactory: ViewHolderFactory) = apply {
        factories.add(viewHolderFactory)
        factories.distinctBy { it.layoutId() }
    }

    fun setOnItemClick(onItemClick: (view: View, itemModel: ItemModel) -> Unit) = apply {
        this.onItemClickListener = onItemClick
    }

    override fun getItemId(position: Int) = getItem(position).id()

    override fun getItemViewType(position: Int): Int = getItem(position).layoutId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return factories.firstOrNull { it.layoutId() == viewType }
            ?.createViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
            .also {
                it?.onItemClick = this.onItemClickListener
            } ?: throw Throwable(
            "ViewHolder of ${parent.context.resources.getResourceEntryName(viewType)} is not register yet"
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        holder.itemView.tag = getItem(position)
        factories.firstOrNull { it.layoutId() == getItemViewType(position) }
            ?.bindViewHolder(holder, getItem(position))
    }
}