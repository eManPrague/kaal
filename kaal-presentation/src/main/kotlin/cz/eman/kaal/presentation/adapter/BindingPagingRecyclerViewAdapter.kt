package cz.eman.kaal.presentation.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder

/**
 * The common implementation of [PagingDataAdapter]. Basic functionality is handled in [BaseBindingAdapter] to prevent
 * duplication of code between adapters.
 *
 * The click listeners are registered only if the item view support the click action ([android.R.attr.clickable],
 * [android.R.attr.longClickable]).
 *
 * @author eMan a.s.
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @see BaseBindingAdapter
 * @since 0.9.0
 */
@Suppress("UNCHECKED_CAST", "unused")
class BindingPagingRecyclerViewAdapter<T : Any>(
    config: BindingAdapterConfig<T>,
    differ: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BaseBindingAdapter.ViewHolder>(differ),
    BaseBindingAdapter<T> {

    override val itemBinder: ItemBinder<T> = config.itemBinder
    override val itemClickListener: ((View, T) -> Unit)? = config.itemClickListener
    override val itemLongClickListener: ((View, T) -> Unit)? = config.itemLongClickListener
    override val variableBinders: Array<VariableBinder<T>>? = config.variableBinders
    private val limit: Int? = config.limit

    /**
     * @see BaseBindingAdapter.getItemInternal
     * @see getItem
     */
    override fun getItemInternal(position: Int): T? = getItem(position)

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
     * Gets item count and ensures that is is not higher than the [limit] (if set). Else it just returns item count from
     * super.
     *
     * @return [Int] item count
     * @see PagingDataAdapter.getItemCount
     * @see Int.coerceAtMost
     */
    override fun getItemCount() = super.getItemCount().coerceAtMost(limit ?: Int.MAX_VALUE)
}
