package cz.eman.kaal.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import cz.eman.kaal.presentation.R
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder

/**
 * Handles basic (common) functionality for Binding adapters. Takes care of view creation, data binding, click listeners
 * and other common functionality for adapters.
 *
 * This is an interface due to different parents for adapters. It accesses the variable by functions and
 *
 * @author eMan a.s.
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @see BaseBindingAdapter
 * @since 0.9.0
 */
@Suppress("UNCHECKED_CAST")
interface BaseBindingAdapter<T : Any> : View.OnClickListener,
    View.OnLongClickListener {

    fun getItemInternal(@IntRange(from = 0) position: Int): T?

    val itemBinder: ItemBinder<T>
    val itemClickListener: ((View, T) -> Unit)?
    val itemLongClickListener: ((View, T) -> Unit)?
    val variableBinders: Array<VariableBinder<T>>?

    fun onCreateViewHolderInternal(viewGroup: ViewGroup, @LayoutRes layoutId: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), layoutId, viewGroup, false))
    }

    fun onBindViewHolderInternal(viewHolder: ViewHolder, position: Int) {
        val item = getItemInternal(position) ?: return
        viewHolder.binding.apply {
            lifecycleOwner = viewHolder

            setVariable(itemBinder.getBindingVariable(item), item)
            variableBinders?.forEach {
                setVariable(
                    it.getVariableId(item),
                    it.getVariableValue(item)
                )
            }
        }.executePendingBindings()

        viewHolder.binding.root.apply {
            setTag(R.id.recycler_view_adapter_item_model, item)
            isClickable = isClickable.also { setOnClickListener(this@BaseBindingAdapter) }
            isLongClickable = isLongClickable.also { setOnLongClickListener(this@BaseBindingAdapter) }
        }
    }

    fun onViewAttachedToWindowInternal(holder: ViewHolder) {
        holder.onStart()
    }

    fun onViewDetachedFromWindowInternal(holder: ViewHolder) {
        holder.onStop()
    }

    @LayoutRes
    fun getItemViewTypeInternal(position: Int) =
        getItemInternal(position)?.let { itemBinder.getLayoutRes(it) } ?: 0

    override fun onClick(v: View) {
        itemClickListener?.let {
            val item = v.getTag(R.id.recycler_view_adapter_item_model) as T
            it.invoke(v, item)
        }
    }

    override fun onLongClick(v: View): Boolean {
        itemLongClickListener?.let {
            val item = v.getTag(R.id.recycler_view_adapter_item_model) as T
            it.invoke(v, item)
            return true
        }
        return false
    }

    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root),
        LifecycleOwner {

        private val registry: LifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle = registry

        fun onStart() {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        fun onStop() {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }
}
