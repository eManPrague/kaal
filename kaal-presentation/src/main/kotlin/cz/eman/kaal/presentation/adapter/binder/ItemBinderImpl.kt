package cz.eman.kaal.presentation.adapter.binder

import androidx.annotation.LayoutRes

/**
 * The basic implementation of [ItemBinder]
 *
 * @author eMan s.r.o.
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @since 0.8.0
 */
open class ItemBinderImpl<in T : Any>(
    private val bindingVariable: Int,
    @LayoutRes private val layoutId: Int
) : ItemBinder<T> {

    override fun getBindingVariable(itemModel: T) = bindingVariable

    override fun getLayoutRes(itemModel: T) = layoutId
}
