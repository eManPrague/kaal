package cz.eman.kaal.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import cz.eman.kaal.presentation.adapter.binder.ItemBinder
import cz.eman.kaal.presentation.adapter.binder.VariableBinder

/**
 * Wrapper data class for Binding adapter configuration. It is used when binding adapter is being created. Holds only
 * common information and all extending information must be handled separately.
 *
 * @property itemBinder defines layout for specific item types
 * @property variableBinders allows sending custom variables to the layout
 * @property itemClickListener enables to handle item click (not sub-view click)
 * @property itemLongClickListener enables to handle item long click (not sub-view click)
 * @property limit of how many items can be displayed
 * @property differ used to compare items
 * @author: eMan a.s.
 * @since 0.9.0
 */
class BindingAdapterConfig<T : Any>(
    val itemBinder: ItemBinder<T>,
    val variableBinders: Array<VariableBinder<T>>?,
    val itemClickListener: ((View, T) -> Unit)?,
    val itemLongClickListener: ((View, T) -> Unit)?,
    val limit: Int? = null,
    val differ: DiffUtil.ItemCallback<T>? = null
)
