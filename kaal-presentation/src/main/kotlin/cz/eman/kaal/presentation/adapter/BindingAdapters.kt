package cz.eman.kaal.presentation.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder

/**
 * Binds adapter to [RecyclerView] using [BindingRecyclerViewAdapter]. Parameters are used to create this adapter and
 * support item clicks, differences and other options. If the adapter already exists it only changes item list in
 * the adapter, which happens with Observable or LiveData items.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 0.8.0
 */
@BindingAdapter(
    value = ["items", "itemBinder", "variableBinders", "itemOnClick", "itemOnLongClick", "differ", "limit", "onComplete"],
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
    onAdapterCreated: ((RecyclerView) -> Unit)?,
    onItemsSet: ((RecyclerView) -> Unit)?
) {
    var adapter = recyclerView.adapter
    if (adapter == null) {
        requireNotNull(binder) { "ItemBinder must be defined" }

        adapter = BindingRecyclerViewAdapter(
            items = items,
            itemBinder = binder,
            itemClickListener = clickListener,
            itemLongClickListener = longClickListener,
            variableBinders = variables,
            differ = differ,
            limit = limit
        )
        recyclerView.adapter = adapter
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
    value = ["items", "itemBinder", "variableBinders", "itemOnClick", "itemOnLongClick", "differ", "onComplete"],
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
    onAdapterCreated: ((ViewPager2) -> Unit)?,
    onItemsSet: ((ViewPager2) -> Unit)?
) {
    var adapter = viewPager.adapter
    if (adapter == null) {
        requireNotNull(binder) { "ItemBinder must be defined" }

        adapter = BindingRecyclerViewAdapter(
            items = items,
            itemBinder = binder,
            itemClickListener = clickListener,
            itemLongClickListener = longClickListener,
            variableBinders = variables,
            differ = differ
        )
        viewPager.adapter = adapter
        onAdapterCreated?.invoke(viewPager)
    } else {
        @Suppress("UNCHECKED_CAST")
        adapter as BindingRecyclerViewAdapter<T>
        adapter.setItems(items)
        onItemsSet?.invoke(viewPager)
    }
}
