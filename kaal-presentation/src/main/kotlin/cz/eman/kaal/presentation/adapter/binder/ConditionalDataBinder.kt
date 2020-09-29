package cz.eman.kaal.presentation.adapter.binder

import androidx.annotation.LayoutRes

/**
 * The implementation of [ItemBinder] which adds support for multiple types of view items
 *
 * @author eMan s.r.o.
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @since 0.8.0
 */
abstract class ConditionalDataBinder<in T : Any>(
    bindingVariable: Int,
    @LayoutRes layoutId: Int
) : ItemBinderImpl<T>(bindingVariable, layoutId) {

    /**
     * Check if `this` can be used for creating and binding view for [itemModel]. Can be for example `itemModel is Model`
     * and second binder can be `itemModel is Model2`.
     *
     * @param itemModel Model of item
     * @return `true` if `this` can be used for creating and binding view for [itemModel] otherwise `false`
     */
    abstract fun canHandle(itemModel: T): Boolean
}
