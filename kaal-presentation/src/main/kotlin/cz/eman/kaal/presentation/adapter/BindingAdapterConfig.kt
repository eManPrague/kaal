package cz.eman.kaal.presentation.adapter

import android.view.View
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder

/**
 * Wrapper data class for configuration of Binding adapter.
 *
 * @author: eMan a.s.
 */
data class BindingAdapterConfig<T : Any>(
    val items: Collection<T>?,
    val itemBinder: ItemBinder<T>,
    val variableBinders: Array<VariableBinder<T>>?,
    val itemClickListener: ((View, T) -> Unit)?,
    val itemLongClickListener: ((View, T) -> Unit)?,
    val limit: Int? = null
)
