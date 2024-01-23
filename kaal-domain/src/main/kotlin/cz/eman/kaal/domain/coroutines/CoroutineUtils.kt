package cz.eman.kaal.domain.coroutines

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CancellationException

/**
 * The convenient method to invoke a [block] and react on optional [CancellationException].
 *
 * @param T The return type of the method.
 * @param onCancelled Called before [CancellationException] is re-thrown. Lambda is called in [NonCancellable] context.
 * @param block The lambda to invoke.
 * @return The result of the [block] or throws [CancellationException].
 */
suspend fun <T> cancellationAware(
    onCancelled: suspend () -> Unit,
    block: suspend () -> T,
): T =
    try {
        block()
    } catch (ex: CancellationException) {
        withContext(NonCancellable) {
            onCancelled.invoke()
        }
        throw ex
    }
