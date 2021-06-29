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
    override val itemBinder: ItemBinder<T>,
    override val itemClickListener: ((View, T) -> Unit)? = null,
    override val itemLongClickListener: ((View, T) -> Unit)? = null,
    override val variableBinders: Array<VariableBinder<T>>? = null,
    private val limit: Int? = null,
    differ: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BaseBindingAdapter.ViewHolder>(differ),
    BaseBindingAdapter<T> {

    override fun getItemInternal(position: Int): T? = getItem(position)

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

    override fun getItemCount() = super.getItemCount().coerceAtMost(limit ?: Int.MAX_VALUE)
}
