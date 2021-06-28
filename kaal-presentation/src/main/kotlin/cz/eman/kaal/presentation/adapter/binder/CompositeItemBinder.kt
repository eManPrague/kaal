package cz.eman.kaal.presentation.adapter.binder

/**
 * The implementation of [ItemBinder] which support multiple types of view items
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @author Radek Piekarz
 * @see [GitHub project](https://github.com/radzio/android-data-binding-recyclerview)
 * @since 0.8.0
 */
class CompositeItemBinder<in T : Any>(vararg conditionalDataBinders: ConditionalDataBinder<T>) : ItemBinder<T> {

    private val conditionalDataBinders = arrayOf(*conditionalDataBinders)

    override fun getBindingVariable(itemModel: T): Int {
        return conditionalDataBinders.find { it.canHandle(itemModel) }
            ?.getBindingVariable(itemModel)
            ?: throw IllegalStateException("Unknown item binder for ${itemModel::class.java.name}")
    }

    override fun getLayoutRes(itemModel: T): Int {
        return conditionalDataBinders.find { it.canHandle(itemModel) }
            ?.getLayoutRes(itemModel)
            ?: throw IllegalStateException("Unknown item binder for ${itemModel::class.java.name}")
    }
}
