package cz.eman.kaal.presentation.util

import androidx.recyclerview.widget.DiffUtil

/**
 * A Callback class used by [DiffUtil.Callback] while calculating the diff between two lists.
 *
 * @param T Type of the item
 * @author eMan s.r.o.
 * @see DiffUtil.Callback
 * @since 0.8.0
 */
interface DiffCallback<in T> {

    /**
     * Called by the [DiffUtil.Callback] to decide whether two object represent the same Item.
     *
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @param oldItem The item in the old list
     * @param newItem The item in the new list
     * @return `true` if the two items represent the same object or `false` if they are different.
     */
    fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    /**
     * Called by the [DiffUtil.Callback] when it wants to check whether two items have the same data.
     * [DiffUtil.Callback] uses this information to detect if the contents of an item has changed.
     *
     * [DiffUtil.Callback] uses this method to check equality instead of [Any.equals]
     * so that you can change its behavior depending on your UI.
     * For example, if you are using [DiffUtil.Callback] with a [androidx.recyclerview.widget.RecyclerView.Adapter],
     * you should return whether the items' visual representations are the same.
     *
     * This method is called only if [areItemsTheSame] returns `true` for these items.
     *
     * @param oldItem The item in the old list
     * @param newItem The item in the new list which replaces the oldItem
     * @return `true` if the contents of the items are the same or `false` if they are different.
     */
    fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}

/**
 * Implementation of [DiffUtil.Callback] using the [DiffCallback] implementation to compare items
 *
 * @param T Type of the item
 *
 * @param oldItems The list of old items
 * @param newItems The list of new items which replace the [oldItems]
 * @param differ The implementation of comparative callback
 * @author eMan s.r.o.
 * @see DiffUtil.Callback
 * @see DiffCallback
 * @since 0.8.0
 */
class DiffUtilCallback<in T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val differ: DiffCallback<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        differ.areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        differ.areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
}
