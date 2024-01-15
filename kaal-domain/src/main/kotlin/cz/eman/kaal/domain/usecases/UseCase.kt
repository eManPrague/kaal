package cz.eman.kaal.domain.usecases

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This class represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * Use cases are the entry points to the domain layer.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 1.0.0
 */
abstract class UseCase<out T, in Params> {

    /**
     * Executes appropriate implementation of [UseCase],
     * @param params Set of input parameters
     * @return type [T] of parameter. In the most common way the [T] is wrapped to a special use-case implementation.
     */
    suspend operator fun invoke(params: Params): T =
        try {
            doWork(params)
        } catch (ex: CancellationException) {
            withContext(NonCancellable) {
                onCancelled(params)
            }
            throw ex
        }

    /**
     * Inner business logic of [UseCase]
     *
     * @param params Set of input parameters
     * @return type [T] of parameter. In the most common way the [T] is wrapped to a special use-case implementation.
     */
    protected abstract suspend fun doWork(params: Params): T

    /**
     * Called before [CancellationException] is re-thrown. Function is called in [NonCancellable] context.
     *
     * @param params Set of input parameters
     * @see NonCancellable
     */
    protected open suspend fun onCancelled(params: Params) {
        // Empty
    }
}
