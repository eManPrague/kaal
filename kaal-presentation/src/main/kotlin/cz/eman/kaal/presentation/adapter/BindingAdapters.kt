package cz.eman.kaal.presentation.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder
import cz.eman.kaal.presentation.util.DiffCallback

/**
 * Binds adapter to [RecyclerView] using [BindingRecyclerViewAdapter]. Parameters are used to create this adapter and
 * support item clicks, differences and other options. If the adapter already exists it only changes item list in
 * the adapter, which happens with Observable or LiveData items.
 *
 * @author eMan s.r.o.
 * @since 0.8.0
 */
@BindingAdapter(
    value = ["items", "itemBinder", "variableBinders", "itemOnClick", "itemOnLongClick", "differ", "limit"],
    requireAll = false
)
fun <T : Any> bindRecyclerView(
    recyclerView: RecyclerView,
    items: Collection<T>?,
    binder: ItemBinder<T>?,
    variables: Array<VariableBinder<T>>?,
    clickListener: ((View, T) -> Unit)?,
    longClickListener: ((View, T) -> Unit)?,
    differ: DiffCallback<T>?,
    limit: Int?
) {
    val adapter = recyclerView.adapter
    if (adapter == null) {
        requireNotNull(binder) { "ItemBinder must be defined" }

        recyclerView.adapter = BindingRecyclerViewAdapter(
            items = items,
            itemBinder = binder,
            itemClickListener = clickListener,
            itemLongClickListener = longClickListener,
            variableBinders = variables,
            differ = differ,
            limit = limit
        )
    } else {
        @Suppress("UNCHECKED_CAST")
        adapter as BindingRecyclerViewAdapter<T>
        adapter.setItems(items)
    }
}

/**
 * Binds adapter to [ViewPager2] using [BindingRecyclerViewAdapter]. Parameters are used to create this adapter and
 * support item clicks, differences and other options. If the adapter already exists it only changes item list in
 * the adapter, which happens with Observable or LiveData items.
 *
 * @author eMan s.r.o.
 * @since 0.8.0
 */
@BindingAdapter(
    value = ["items", "itemBinder", "variableBinders", "itemOnClick", "itemOnLongClick", "differ"],
    requireAll = false
)
fun <T : Any> bindViewPager2(
    viewPager: ViewPager2,
    items: Collection<T>?,
    binder: ItemBinder<T>?,
    variables: Array<VariableBinder<T>>?,
    clickListener: ((View, T) -> Unit)?,
    longClickListener: ((View, T) -> Unit)?,
    differ: DiffCallback<T>?
) {
    val adapter = viewPager.adapter
    if (adapter == null) {
        requireNotNull(binder) { "ItemBinder must be defined" }

        viewPager.adapter = BindingRecyclerViewAdapter(
            items = items,
            itemBinder = binder,
            itemClickListener = clickListener,
            itemLongClickListener = longClickListener,
            variableBinders = variables,
            differ = differ
        )
    } else {
        @Suppress("UNCHECKED_CAST")
        adapter as BindingRecyclerViewAdapter<T>
        adapter.setItems(items)
    }
}
