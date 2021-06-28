package cz.eman.kaal.presentation.adapter.binder

/**
 * The implementation of [VariableBinder]
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 0.8.0
 */
data class VariableBinderImpl<in T : Any>(
    private val id: Int,
    private val value: Any?
) : VariableBinder<T> {

    override fun getVariableId(itemModel: T) = id

    override fun getVariableValue(itemModel: T) = value
}
