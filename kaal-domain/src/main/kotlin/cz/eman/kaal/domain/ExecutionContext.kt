package cz.eman.kaal.domain

/**
 * ```
 * val result = Result.Success("Hello World!")
 * val error = Result.Error(ErrorResult("Some error text"))
```
 *
 * @author vaclav.souhrada@eman.cz
 * @since 1.0.0
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error(val error: ErrorResult) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=${error.throwable}"
        }
    }
}

open class ErrorResult(open var message: String? = null, open var throwable: Throwable? = null)

/**
 *
 * @author Roman Holomek <roman.holomek@eman.cz>
 * @since 1.0.0
 */
data class ApiErrorResult(
    val code: Int,
    val errorMessage: String? = null,
    val apiThrowable: Throwable? = null
) : ErrorResult(errorMessage, apiThrowable)


/**
 * Wrap a suspending [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 *
 * @since 1.0.0
 */
suspend fun <T : Any> callSafe(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Throwable) {
        Result.Error(ErrorResult(errorMessage, e))
    }
}

