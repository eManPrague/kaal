package cz.eman.kaal.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.paging.PagingDataAdapter
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

    /**
     * Callback informing that the list has changes. Only as a [WeakReference] to prevent leaks.
     */
    private val onListChangedCallback: WeakReferenceOnListChangedCallback<T> = WeakReferenceOnListChangedCallback(this)

    /**
     * List of the items being displayed in this adapter. [ObservableList] supports observing list changes.
     */
    var items: ObservableList<T>? = null
        private set

    init {
        setItems(items)
    }

    /**
     * Gets the specific items from the [items] list.
     *
     * @see BaseBindingAdapter.getItemInternal
     */
    override fun getItemInternal(position: Int): T? = items?.get(position)

    /**
     * Removes the list [onListChangedCallback] when this adapter is detached from the [RecyclerView].
     *
     * @param recyclerView to have the listener detached
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        items?.removeOnListChangedCallback(onListChangedCallback)
    }

    /**
     * @see PagingDataAdapter.onCreateViewHolder
     * @see BaseBindingAdapter.onCreateViewHolderInternal
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, @LayoutRes layoutId: Int): BaseBindingAdapter.ViewHolder {
        return onCreateViewHolderInternal(viewGroup, layoutId)
    }

    /**
     * @see PagingDataAdapter.onBindViewHolder
     * @see BaseBindingAdapter.onBindViewHolderInternal
     */
    override fun onBindViewHolder(viewHolder: BaseBindingAdapter.ViewHolder, position: Int) {
        onBindViewHolderInternal(viewHolder, position)
    }

    /**
     * @see PagingDataAdapter.onViewAttachedToWindow
     * @see BaseBindingAdapter.onViewAttachedToWindowInternal
     */
    override fun onViewAttachedToWindow(holder: BaseBindingAdapter.ViewHolder) {
        onViewAttachedToWindowInternal(holder)
    }

    /**
     * @see PagingDataAdapter.onViewDetachedFromWindow
     * @see BaseBindingAdapter.onViewDetachedFromWindowInternal
     */
    override fun onViewDetachedFromWindow(holder: BaseBindingAdapter.ViewHolder) {
        onViewDetachedFromWindowInternal(holder)
    }

    /**
     * @see PagingDataAdapter.getItemViewType
     * @see BaseBindingAdapter.getItemViewTypeInternal
     */
    @LayoutRes
    override fun getItemViewType(position: Int) = getItemViewTypeInternal(position)

    /**
     * Gets item count from [items] variable and ensures that is is not higher than the [limit] (if set). Else it just
     * returns item count from [items].
     *
     * @return [Int] item count
     * @see Int.coerceAtMost
     */
    override fun getItemCount() = (items?.size ?: 0).coerceAtMost(limit ?: Int.MAX_VALUE)

    /**
     * Allows to set new item list to this adapter. Calculates differences and notifies the adapter about changes. If
     * the list has changed, else it does nothing.
     *
     * @param items new items to be displayed
     */
    fun setItems(items: Collection<T>?) {
        if (this.items === items) {
            return
        }

        val oldItems = this.items
        if (items == null) {
            handleNewEmptyItems(oldItems)
        } else {
            handleNewItems(oldItems, items)
        }
    }

    /**
     * Handles case where new items are empty (null). Removes the [onListChangedCallback], clears current item list and
     * notifies using [notifyItemRangeRemoved].
     *
     * @param oldItems to have callback removed
     */
    private fun handleNewEmptyItems(oldItems: ObservableList<T>?) {
        oldItems?.let {
            it.removeOnListChangedCallback(onListChangedCallback)
            this.items = null
            notifyItemRangeRemoved(0, it.size)
        }
    }

    /**
     * Handles new items added to the adapter. First it adds [onListChangedCallback] to new list and then it handles new
     * items with multiple cases:
     * 1) When [oldItems] are null it sets them to the [items] and notifies using [notifyItemRangeInserted].
     * 2) When [oldItems] are not null and [differ] is not null then new items are handled using [handleNewItemsDiffer].
     * 3) Else items are handles using [handleNewItemsNoDiffer].
     *
     * @param oldItems items currently displayed in the list
     * @param items new items to be displayed in the list
     * @see notifyItemRangeInserted
     * @see handleNewItemsDiffer
     * @see handleNewItemsNoDiffer
     */
    private fun handleNewItems(oldItems: ObservableList<T>?, items: Collection<T>) {
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
                handleNewItemsDiffer(oldItems, newItems, differ)
            } else {
                handleNewItemsNoDiffer(oldItems, newItems)
            }
        }
    }

    /**
     * Handles new items displayed in the list using a differ. Calculates differences, removes [onListChangedCallback]
     * from the [oldItems] list, sets new items to the list and dispatches the updates using [DiffUtil].
     *
     * @param oldItems items currently displayed in the list
     * @param newItems new items to be displayed in the list
     * @param differ used to calculate differences between two lists
     * @see DiffUtil.calculateDiff
     * @see DiffUtil.DiffResult.dispatchUpdatesTo
     */
    private fun handleNewItemsDiffer(
        oldItems: ObservableList<T>,
        newItems: ObservableList<T>,
        differ: DiffUtil.ItemCallback<T>
    ) {
        val result = DiffUtil.calculateDiff(DiffCallback(oldItems, newItems, differ))
        oldItems.removeOnListChangedCallback(onListChangedCallback)
        this.items = newItems
        result.dispatchUpdatesTo(this)
    }

    /**
     * Handles new items displayed in the list without a differ. Handles cases:
     * 1) [oldItems] size is lower than [newItems] size
     * 2) [oldItems] size is higher than [newItems] size
     * 1) [oldItems] size equal to the size of [newItems]
     *
     * @param oldItems items currently displayed in the list
     * @param newItems new items to be displayed in the list
     * @see notifyItemRangeChanged
     * @see notifyItemRangeInserted
     * @see notifyItemRangeRemoved
     */
    private fun handleNewItemsNoDiffer(oldItems: ObservableList<T>, newItems: ObservableList<T>) {
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

    /**
     * Class observing changes in the list and notifying the recycler view about changes. Contains [WeakReference] to
     * the adapter which should be notified about list changes.
     *
     * @param recyclerViewAdapter adapter instance which will be wrapped in [WeakReference]
     * @see ObservableList.OnListChangedCallback
     * @see ObservableList
     */
    private class WeakReferenceOnListChangedCallback<T>(
        recyclerViewAdapter: RecyclerView.Adapter<*>
    ) : ObservableList.OnListChangedCallback<ObservableList<T>>() {

        /**
         * [WeakReference] to the adapter which should be notified about list changes.
         */
        private val adapterReference = WeakReference(recyclerViewAdapter)

        /**
         * @see ObservableList.OnListChangedCallback.onChanged
         * @see RecyclerView.Adapter.notifyDataSetChanged
         */
        override fun onChanged(sender: ObservableList<T>) {
            adapterReference.get()?.notifyDataSetChanged()
        }

        /**
         * @see ObservableList.OnListChangedCallback.onItemRangeChanged
         * @see RecyclerView.Adapter.notifyItemRangeChanged
         */
        override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeChanged(positionStart, itemCount)
        }

        /**
         * @see ObservableList.OnListChangedCallback.onItemRangeInserted
         * @see RecyclerView.Adapter.notifyItemRangeInserted
         */
        override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeInserted(positionStart, itemCount)
        }

        /**
         * @see ObservableList.OnListChangedCallback.onItemRangeMoved
         * @see RecyclerView.Adapter.notifyItemMoved
         */
        override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemMoved(fromPosition, toPosition)
        }

        /**
         * @see ObservableList.OnListChangedCallback.onItemRangeRemoved
         * @see RecyclerView.Adapter.notifyItemRangeRemoved
         */
        override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    companion object {

        /**
         * Build function which creates an instance of this adapter with the specific configuration.
         *
         * @param config used to build this adapter
         * @param differ used to build this adapter, allows to make differences between the items
         * @return [BindingRecyclerViewAdapter]
         */
        fun <T : Any> build(config: BindingAdapterConfig<T>, differ: DiffUtil.ItemCallback<T>?) =
            BindingRecyclerViewAdapter(
                items = config.items,
                itemBinder = config.itemBinder,
                itemClickListener = config.itemClickListener,
                itemLongClickListener = config.itemLongClickListener,
                variableBinders = config.variableBinders,
                limit = config.limit,
                differ = differ
            )
    }
}
