package cz.eman.kaal.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.coroutineContext

/**
 * ```
 * val result = Result.Success("Hello World!")
 * val error = Result.Error(ErrorResult("Some error text"))
```
 *
 * @author vaclav.souhrada@eman.cz
 * @since 0.1.0
 */
sealed class Result<out T : Any> {

    //data class Running<out T : Any>(val data: T? = null) : Result<T>()

    data class Success<out T : Any>(val data: T) : Result<T>()

    data class Error<out T : Any>(val error: ErrorResult, val data: T? = null) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=${error.throwable}"
            //is Running -> "Running[cachedData=$data]"
        }
    }

    fun isFinished() = this is Success || this is Error

    //fun isRunning() = this is Running

    fun isSuccess() = this is Success

    fun isError() = this is Error

    /**
     * Returns the encapsulated value if this instance represents [Success] or cached data when available in [Running] state
     */
    fun getOrNull() = when {
        this is Success -> data
        //this is Running -> data
        this is Error -> data
        else -> null
    }

}

open class ErrorResult(open var message: String? = null, open var throwable: Throwable? = null)

/**
 * Wrap a suspending [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> callSafe(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Throwable) {
        Result.Error(ErrorResult(errorMessage, e))
    }
}

/**
 *  Method for sequential call to local persistence and server request
 *
 *  @param[localCall] Call to local persistence (cache, db, prefs etc.)
 *  @param[remoteCall] Call to server
 *
 *  @return [ReceiveChannel] emitting loaded data with [Result]
 *  @since 0.2.0
 */
/*suspend fun <T : Any> combinedCall(
    localCall: suspend () -> T?,
    remoteCall: suspend () -> Result<T>
): ReceiveChannel<Result<T>> = CoroutineScope(coroutineContext).produce {
    // Send info about running process with empty data
    send(Result.Running<T>())
    // TODO improve content of this method to run both task concurrently - needs to manage correct sequence of states!!
    // Send info about running process with local cached data if available
    val cachedLocalData = localCall()
    send(Result.Running(cachedLocalData))

    // Send result of remote request
    val remoteResult = when (val remoteCallResult = remoteCall()) {
        is Result.Error -> remoteCallResult.copy(data = cachedLocalData)
        else -> remoteCallResult
    }

    send(remoteResult)
}*/

