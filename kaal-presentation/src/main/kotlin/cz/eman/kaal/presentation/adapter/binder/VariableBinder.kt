package cz.eman.kaal.presentation.adapter.binder

/**
 * The interface declares methods for binding variable to item view. Use this if you want to bind custom variable into
 * item view. Most common variable being binded is view model.
 *
 * @param T Type of binding item model
 * @author eMan s.r.o.
 * @since 0.8.0
 */
interface VariableBinder<in T : Any> {

    /**
     * Gets the variable id representing variable in view. For example it can be BR.viewModel.
     *
     * @param itemModel Model of item
     * @return Integer which represents variable in binding layout
     */
    fun getVariableId(itemModel: T): Int

    /**
     * Actual value that will be set into the variable in the view. Sets it into specified variable defined using
     * [getVariableId] function.
     *
     * @param itemModel Model of item
     * @return Value of binding variable
     */
    fun getVariableValue(itemModel: T): Any?
}
