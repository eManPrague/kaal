@file:Suppress("unused")

package cz.eman.kaal.presentation.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder
import cz.eman.logger.logError

/**
 * Binds adapter to [RecyclerView] using [BindingRecyclerViewAdapter] or [BindingPagingRecyclerViewAdapter] (based on
 * the [paging] variable). Parameters are used to create adapter and support item clicks, differences and other options.
 * If the adapter already exists it only changes item list in the adapter, which happens with Observable or LiveData
 * items. If the adapter is a pager adapter it does nothing.
 *
 * @author eMan a.s.
 * @since 0.8.0
 */
@BindingAdapter(
    value = ["items", "itemBinder", "variableBinders", "itemOnClick", "itemOnLongClick", "differ", "limit", "paging" , "onAdapterCreated", "onItemsSet"],
    requireAll = false
)
fun <T : Any> bindRecyclerView(
    recyclerView: RecyclerView,
    items: Collection<T>?,
    binder: ItemBinder<T>?,
    variables: Array<VariableBinder<T>>?,
    clickListener: ((View, T) -> Unit)?,
    longClickListener: ((View, T) -> Unit)?,
    differ: DiffUtil.ItemCallback<T>?,
    limit: Int?,
    paging: Boolean = false,
    onAdapterCreated: ((RecyclerView) -> Unit)?,
    onItemsSet: ((RecyclerView) -> Unit)?
) {
    var adapter = recyclerView.adapter
    if (adapter == null) {
        requireNotNull(binder) { "ItemBinder must be defined" }

        val config = BindingAdapterConfig(
            items = items,
            itemBinder = binder,
            variableBinders = variables,
            itemClickListener = clickListener,
            itemLongClickListener = longClickListener,
            limit = limit
        )
        recyclerView.adapter = buildBindingAdapter(config, paging, differ)
        onAdapterCreated?.invoke(recyclerView)
    } else {
        @Suppress("UNCHECKED_CAST")
        adapter as BindingRecyclerViewAdapter<T>
        adapter.setItems(items)
        onItemsSet?.invoke(recyclerView)
    }
}

/**
 * Binds adapter to [ViewPager2] using [BindingRecyclerViewAdapter] or [BindingPagingRecyclerViewAdapter] (based on
 * the [paging] variable). Parameters are used to create adapter and support item clicks, differences and other options.
 * If the adapter already exists it only changes item list in the adapter, which happens with Observable or LiveData
 * items. If the adapter is a pager adapter it does nothing.
 *
 * @author eMan a.s.
 * @since 0.8.0
 */
@BindingAdapter(
    value = ["items", "itemBinder", "variableBinders", "itemOnClick", "itemOnLongClick", "differ", "paging", "onAdapterCreated", "onItemsSet"],
    requireAll = false
)
fun <T : Any> bindViewPager2(
    viewPager: ViewPager2,
    items: Collection<T>?,
    binder: ItemBinder<T>?,
    variables: Array<VariableBinder<T>>?,
    clickListener: ((View, T) -> Unit)?,
    longClickListener: ((View, T) -> Unit)?,
    differ: DiffUtil.ItemCallback<T>?,
    paging: Boolean = false,
    onAdapterCreated: ((ViewPager2) -> Unit)?,
    onItemsSet: ((ViewPager2) -> Unit)?
) {
    var adapter = viewPager.adapter
    if (adapter == null) {
        requireNotNull(binder) { "ItemBinder must be defined" }

        val config = BindingAdapterConfig(
            items = items,
            itemBinder = binder,
            variableBinders = variables,
            itemClickListener = clickListener,
            itemLongClickListener = longClickListener,
        )
        viewPager.adapter = buildBindingAdapter(config, paging, differ)
        onAdapterCreated?.invoke(viewPager)
    } else {
        @Suppress("UNCHECKED_CAST")
        adapter as BindingRecyclerViewAdapter<T>
        adapter.setItems(items)
        onItemsSet?.invoke(viewPager)
    }
}

/**
 * Builds a binding adapter based on the variables. There are three cases at the moment:
 * 1) [BindingRecyclerViewAdapter] is created when paging adapter should not be used.
 * 2) [BindingPagingRecyclerViewAdapter] is created when paging adapter is used and [differ] variable is not null, since
 *    paging adapter requires this variable to be set.
 * 3) Identifies that paging adapter should be created but [differ] is missing. It logs the error and created
 *    [BindingRecyclerViewAdapter] which makes sure data are displayed.
 *
 * @param config common configuration for any biding adapter
 * @param paging true when paging adapter should be created else false
 * @param differ [DiffUtil] to be used in the adapter
 * @author eMan a.s.
 * @see BindingRecyclerViewAdapter.build
 * @see BindingPagingRecyclerViewAdapter.build
 * @since 0.9.0
 */
private fun <T : Any> buildBindingAdapter(
    config: BindingAdapterConfig<T>,
    paging: Boolean,
    differ: DiffUtil.ItemCallback<T>?
): RecyclerView.Adapter<*> = when {
    !paging -> BindingRecyclerViewAdapter.build(config, differ)
    paging && differ != null -> BindingPagingRecyclerViewAdapter.build(config, differ)
    else -> {
        config.logError { "Failed to create BindingPagingRecyclerViewAdapter (no differ supplied)" }
        BindingRecyclerViewAdapter.build(config, differ)
    }
}
