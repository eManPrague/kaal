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
 * Binds adapter to [RecyclerView] using [BindingRecyclerViewAdapter]. Parameters are used to create this adapter and
 * support item clicks, differences and other options. If the adapter already exists it only changes item list in
 * the adapter, which happens with Observable or LiveData items.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
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
        recyclerView.adapter = buildAdapter(config, paging, differ)
        onAdapterCreated?.invoke(recyclerView)
    } else {
        @Suppress("UNCHECKED_CAST")
        adapter as BindingRecyclerViewAdapter<T>
        adapter.setItems(items)
        onItemsSet?.invoke(recyclerView)
    }
}

/**
 * Binds adapter to [ViewPager2] using [BindingRecyclerViewAdapter]. Parameters are used to create this adapter and
 * support item clicks, differences and other options. If the adapter already exists it only changes item list in
 * the adapter, which happens with Observable or LiveData items.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
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
        viewPager.adapter = buildAdapter(config, paging, differ)
        onAdapterCreated?.invoke(viewPager)
    } else {
        @Suppress("UNCHECKED_CAST")
        adapter as BindingRecyclerViewAdapter<T>
        adapter.setItems(items)
        onItemsSet?.invoke(viewPager)
    }
}

private fun <T : Any> buildAdapter(
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
