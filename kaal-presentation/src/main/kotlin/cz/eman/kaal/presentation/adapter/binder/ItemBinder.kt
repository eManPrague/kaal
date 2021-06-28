package cz.eman.kaal.presentation.adapter.binder

import androidx.annotation.LayoutRes

/**
 * The interface declares methods for creating and binding view which represents item f.e. in RecyclerView.
 *
 * @param T Type of binding item model
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @since 0.8.0
 */
interface ItemBinder<in T : Any> {

    /**
     * Gets the variable id representing variable in view. For example it can be BR.item.
     *
     * @param itemModel Model of item
     * @return Integer which represents variable in binding layout
     */
    fun getBindingVariable(itemModel: T): Int

    /**
     * Actual value that will be set into the variable in the view. Sets it into specified variable defined using
     * [getBindingVariable] function.
     *
     * @param itemModel Model of item
     * @return Layout resource ID which represents view for [itemModel]
     */
    @LayoutRes
    fun getLayoutRes(itemModel: T): Int
}
