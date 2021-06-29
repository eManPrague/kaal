package cz.eman.kaal.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder
import cz.eman.kaal.presentation.util.DiffCallback
import java.lang.ref.WeakReference

/**
 * The common implementation of [RecyclerView.Adapter]. The implementation supports observing changes on adapter's
 * items. Basic functionality is handled in [BaseBindingAdapter] to prevent duplication of code between adapters.
 *
 * The click listeners are registered only if the item view support the click action ([android.R.attr.clickable],
 * [android.R.attr.longClickable]).
 *
 * @author eMan a.s.
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @see BaseBindingAdapter
 * @since 0.8.0
 */
@Suppress("UNCHECKED_CAST", "unused")
class BindingRecyclerViewAdapter<T : Any>(
    items: Collection<T>? = null,
    override val itemBinder: ItemBinder<T>,
    override val itemClickListener: ((View, T) -> Unit)? = null,
    override val itemLongClickListener: ((View, T) -> Unit)? = null,
    override val variableBinders: Array<VariableBinder<T>>? = null,
    private val differ: DiffUtil.ItemCallback<T>? = null,
    private val limit: Int? = null
) : RecyclerView.Adapter<BaseBindingAdapter.ViewHolder>(),
    BaseBindingAdapter<T> {

    private val onListChangedCallback: WeakReferenceOnListChangedCallback<T> = WeakReferenceOnListChangedCallback(this)

    var items: ObservableList<T>? = null
        private set

    init {
        setItems(items)
    }

    override fun getItemInternal(position: Int): T? = items?.get(position)

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        items?.removeOnListChangedCallback(onListChangedCallback)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, @LayoutRes layoutId: Int): BaseBindingAdapter.ViewHolder {
        return onCreateViewHolderInternal(viewGroup, layoutId)
    }

    override fun onBindViewHolder(viewHolder: BaseBindingAdapter.ViewHolder, position: Int) {
        onBindViewHolderInternal(viewHolder, position)
    }

    override fun onViewAttachedToWindow(holder: BaseBindingAdapter.ViewHolder) {
        onViewAttachedToWindowInternal(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseBindingAdapter.ViewHolder) {
        onViewDetachedFromWindowInternal(holder)
    }

    @LayoutRes
    override fun getItemViewType(position: Int) = getItemViewTypeInternal(position)

    override fun getItemCount() = (items?.size ?: 0).coerceAtMost(limit ?: Int.MAX_VALUE)

    fun setItems(items: Collection<T>?) {
        if (this.items === items) {
            return
        }

        val oldItems = this.items
        if (items == null) {
            oldItems?.let {
                it.removeOnListChangedCallback(onListChangedCallback)
                this.items = null
                notifyItemRangeRemoved(0, it.size)
            }
        } else {
            val newItems = when (items) {
                is ObservableList -> items
                else -> ObservableArrayList<T>().apply { addAll(items) }
            }
            newItems.addOnListChangedCallback(onListChangedCallback)

            if (oldItems == null) {
                this.items = newItems
                notifyItemRangeInserted(0, newItems.size)
            } else {
                if (differ != null) {
                    val result = DiffUtil.calculateDiff(DiffCallback(oldItems, newItems, differ))
                    oldItems.removeOnListChangedCallback(onListChangedCallback)
                    this.items = newItems
                    result.dispatchUpdatesTo(this)
                } else {
                    val oldSize = oldItems.size
                    val newSize = newItems.size
                    this.items = newItems
                    when {
                        oldSize < newSize -> {
                            notifyItemRangeChanged(0, oldSize)
                            notifyItemRangeInserted(oldSize, newSize - oldSize)
                        }
                        oldSize > newSize -> {
                            notifyItemRangeChanged(0, newSize)
                            notifyItemRangeRemoved(newSize, oldSize - newSize)
                        }
                        else -> notifyItemRangeChanged(0, newSize)
                    }
                }
            }
        }
    }

    private class WeakReferenceOnListChangedCallback<T>(
        recyclerViewAdapter: RecyclerView.Adapter<*>
    ) : ObservableList.OnListChangedCallback<ObservableList<T>>() {

        private val adapterReference = WeakReference(recyclerViewAdapter)

        override fun onChanged(sender: ObservableList<T>) {
            adapterReference.get()?.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }
}
