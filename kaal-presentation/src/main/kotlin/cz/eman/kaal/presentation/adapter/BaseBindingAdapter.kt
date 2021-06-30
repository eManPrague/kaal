package cz.eman.kaal.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
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
 * This is an interface due to different parents for adapters. It requires access to multiple variables from the
 * extending classes like [itemBinder], [itemClickListener], and others. Prevents code being repeated but it also makes
 * the variables public.
 *
 * @property T type of the object handled by this interface
 * @author eMan a.s.
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @see BaseBindingAdapter
 * @since 0.9.0
 */
@Suppress("UNCHECKED_CAST")
interface BaseBindingAdapter<T : Any> : View.OnClickListener,
    View.OnLongClickListener {

    /**
     * Gets item on a specific position in the adapter. Must be implemented by extending class.
     *
     * @param position of the item
     * @return [T] item or null when not found
     */
    fun getItemInternal(@IntRange(from = 0) position: Int): T?

    /**
     * Item binder specifies binding layout for specific item. For more information check [ItemBinder].
     */
    val itemBinder: ItemBinder<T>

    /**
     * Specifies click action for the whole item layout ([ViewHolder]). Click on any sub-view of the holder must be
     * handled separately. Ex: you can send ViewModel using [variableBinders] and bind click directly in layout xml.
     */
    val itemClickListener: ((View, T) -> Unit)?

    /**
     * Same as the [itemClickListener] with the exception that it handles long click instead of click.
     */
    val itemLongClickListener: ((View, T) -> Unit)?

    /**
     * Used to send custom variables to item layout [ViewHolder]. Any variable can be send but most common is ViewModel.
     * For more information check [VariableBinder].
     */
    val variableBinders: Array<VariableBinder<T>>?

    /**
     * Creates instance of [ViewHolder] with specific binding layout inflated using [layoutId].
     *
     * @param viewGroup for the layout inflation
     * @param layoutId layout resource identifying which layout should be inflated
     * @return inflated [ViewHolder]
     * @see ViewHolder
     * @see DataBindingUtil.inflate
     */
    fun onCreateViewHolderInternal(viewGroup: ViewGroup, @LayoutRes layoutId: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), layoutId, viewGroup, false))
    }

    /**
     * Binds item data to the [ViewHolder]. Gets the specific item (if possible) and sets it to the view holder binding.
     * It also sets any other variables from [variableBinders] binds click listeners (if they are enabled).
     *
     * Since [ViewHolder] allows variable observing using [Lifecycle] it will set the binding [LifecycleOwner] as a
     * [ViewHolder]. It makes sure all variable changes are propagated to the binding. After binding is configured it
     * also triggers [ViewDataBinding.executePendingBindings] to make sure all displayed information correspond with the
     * item information.
     *
     * @param viewHolder to have variables and listeners set
     * @param position of the item in the list
     * @see ViewDataBinding.setVariable
     * @see ViewDataBinding.executePendingBindings
     */
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

    /**
     * When view is attached to the window the [ViewHolder] starts observing variables.
     *
     * @param viewHolder to start listening in
     * @see ViewHolder.onStart
     */
    fun onViewAttachedToWindowInternal(viewHolder: ViewHolder) {
        viewHolder.onStart()
    }

    /**
     * When view is detached from the window the [ViewHolder] stops observing variables.
     *
     * @param viewHolder to start listening in
     * @see ViewHolder.onStop
     */
    fun onViewDetachedFromWindowInternal(viewHolder: ViewHolder) {
        viewHolder.onStop()
    }

    /**
     * Gets item view type by it's [position]. Gets the item using [getItemInternal] and then it gets layout resource
     * for it from the [itemBinder]. If it is not found then 0 is returned.
     *
     * @param position of the item in the list
     * @return [Int] with layout resource
     * @see getItemInternal
     * @see ItemBinder.getLayoutRes
     */
    @LayoutRes
    fun getItemViewTypeInternal(position: Int) =
        getItemInternal(position)?.let { itemBinder.getLayoutRes(it) } ?: 0

    /**
     * Handles click on the item view by getting the item from view tag and invoking [itemClickListener] if it exists.
     *
     * @param view used to get the item and also being send to the click listener
     */
    override fun onClick(view: View) {
        itemClickListener?.let {
            val item = view.getTag(R.id.recycler_view_adapter_item_model) as T
            it.invoke(view, item)
        }
    }

    /**
     * Handles long click on the item view by getting the item from view tag and invoking [itemLongClickListener] if it
     * exists.
     *
     * @param view used to get the item and also being send to the click listener
     * @return true if handled by the [itemLongClickListener] else false
     */
    override fun onLongClick(view: View): Boolean {
        itemLongClickListener?.let {
            val item = view.getTag(R.id.recycler_view_adapter_item_model) as T
            it.invoke(view, item)
            return true
        }
        return false
    }

    /**
     * View holder for the [RecyclerView.Adapter] holding [ViewDataBinding] for the item and also serving as a
     * [LifecycleOwner] to listen for any changes in lifecycle events.
     *
     * @property binding view binding for the holder
     * @see RecyclerView.ViewHolder
     */
    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root),
        LifecycleOwner {

        /**
         * @see LifecycleRegistry
         */
        private val registry: LifecycleRegistry = LifecycleRegistry(this)

        /**
         * @see LifecycleOwner.getLifecycle
         */
        override fun getLifecycle(): Lifecycle = registry

        /**
         * Sets [Lifecycle.Event.ON_START] event to the [registry] to start observing variable changes.
         */
        @MainThread
        fun onStart() {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        /**
         * Sets [Lifecycle.Event.ON_STOP] event to the [registry] to stop observing variable changes.
         */
        @MainThread
        fun onStop() {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }
}
