package cz.eman.kaal.presentation.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Implementation of [DiffUtil.Callback] using the [DiffUtil.ItemCallback] to compare items
 *
 * @param T Type of the item
 *
 * @param oldItems The list of old items
 * @param newItems The list of new items which replace the [oldItems]
 * @param differ The implementation of comparative callback
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 *
 * @see DiffUtil.Callback
 * @see DiffUtil.ItemCallback
 *
 * @since 0.8.0
 */
class DiffCallback<in T: Any>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val differ: DiffUtil.ItemCallback<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        differ.areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        differ.areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * The [DiffUtil.ItemCallback.areContentsTheSame] uses the [equals] method to compare items.
 * This class should by used with `data class` or class that is overriding [equals] method.
 *
 * @param T Type of items to compare
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 *
 * @see DiffUtil.ItemCallback
 */
abstract class DiffItemCallback<T> : DiffUtil.ItemCallback<T>() {

    @SuppressLint("DiffUtilEquals")
    final override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean = oldItem == newItem
}

/**
 * @param T Type of items to compare
 * @param ID Type of item identifier
 *
 * @param idSelector Function for selecting the item identifier
 *
 * @return Instance of the [DiffItemCallback]
 *
 * @see DiffItemCallback
 */
inline fun <T, ID> createDiffer(crossinline idSelector: (T) -> ID): DiffUtil.ItemCallback<T> {
    return object : DiffItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean =
            idSelector(oldItem) == idSelector(newItem)
    }
}
