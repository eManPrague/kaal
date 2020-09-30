package cz.eman.kaal.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cz.eman.kaal.presentation.R
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder
import cz.eman.kaal.presentation.util.DiffCallback
import java.lang.ref.WeakReference

/**
 * The common implementation of [RecyclerView.Adapter].
 * The implementation supports observing changes on adapter's items.
 *
 * The click listeners are registered only if the item view support the click action
 * ([android.R.attr.clickable], ([android.R.attr.longClickable]).
 *
 * @author eMan s.r.o.
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @since 0.8.0
 */
@Suppress("UNCHECKED_CAST")
class BindingRecyclerViewAdapter<T : Any>(
    items: Collection<T>? = null,
    private val itemBinder: ItemBinder<T>,
    private val itemClickListener: ((View, T) -> Unit)? = null,
    private val itemLongClickListener: ((View, T) -> Unit)? = null,
    private val variableBinders: Array<VariableBinder<T>>? = null,
    private val differ: DiffUtil.ItemCallback<T>? = null,
    private val limit: Int? = null
) : RecyclerView.Adapter<BindingRecyclerViewAdapter.ViewHolder>(),
    View.OnClickListener,
    View.OnLongClickListener {

    private val onListChangedCallback: WeakReferenceOnListChangedCallback<T>

    var items: ObservableList<T>? = null
        private set

    init {
        onListChangedCallback = WeakReferenceOnListChangedCallback(this)
        setItems(items)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        items?.removeOnListChangedCallback(onListChangedCallback)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, @LayoutRes layoutId: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), layoutId, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items!![position]
        viewHolder.binding.apply {
            lifecycleOwner = viewHolder

            setVariable(itemBinder.getBindingVariable(item), item)
            variableBinders?.forEach { setVariable(it.getVariableId(item), it.getVariableValue(item)) }
            root.apply {
                setTag(R.id.recycler_view_adapter_item_model, item)
                if (isClickable) setOnClickListener(this@BindingRecyclerViewAdapter)
                if (isLongClickable) setOnLongClickListener(this@BindingRecyclerViewAdapter)
            }
        }.executePendingBindings()
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        holder.onStart()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.onStop()
    }

    @LayoutRes
    override fun getItemViewType(position: Int) = items?.let { itemBinder.getLayoutRes(it[position]) } ?: 0

    override fun getItemCount() = (items?.size ?: 0).coerceAtMost(limit ?: Int.MAX_VALUE)

    override fun onClick(v: View) {
        if (itemClickListener != null) {
            val item = v.getTag(R.id.recycler_view_adapter_item_model) as T
            itemClickListener.invoke(v, item)
        }
    }

    override fun onLongClick(v: View): Boolean {
        if (itemLongClickListener != null) {
            val item = v.getTag(R.id.recycler_view_adapter_item_model) as T
            itemLongClickListener.invoke(v, item)
            return true
        }
        return false
    }

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

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {

        private val registry: LifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = registry

        fun onStart() {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        fun onStop() {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
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
