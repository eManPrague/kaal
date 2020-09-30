package cz.eman.kaal.domain.result

/**
 * ```
 * val result = Result.Success("Hello World!")
 * val error = Result.Error(ErrorResult("Some error text"))
 * ```
 *
 * @author vaclav.souhrada@eman.cz
 * @since 0.1.0
 */
sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Error<out T>(val error: ErrorResult, val data: T? = null) : Result<T>()

    companion object {

        fun <T> success(data: T): Result<T> =
            Success(data = data)

        fun <T> error(error: ErrorResult, data: T? = null): Result<T> =
            Error(error, data)

        fun <T> error(
            errorCode: ErrorCode,
            data: T? = null,
            message: String? = null,
            throwable: Throwable? = null
        ): Result<T> =
            Error(ErrorResult(code = errorCode, message = message, throwable = throwable), data)
    }

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=${error.throwable}"
        }
    }

    fun isFinished() = this is Success || this is Error

    fun isSuccess() = this is Success

    fun isError() = this is Error

    /**
     * Returns the encapsulated value if this instance represents [Success] or null is returned
     */
    fun getOrNull() = if (this is Success) data else null
}

interface ErrorCode {

    companion object {
        val UNDEFINED = errorCode(-1)
    }

    /**
     * Integer representation of the error
     */
    val value: Int
}

/**
 * The error code builder
 *
 * @param code Integer representation of the error
 *
 * @see ErrorCode
 */
fun errorCode(code: Int) = object : ErrorCode {
    override val value: Int = code

    override fun equals(other: Any?): Boolean = this === other || (other is ErrorCode && value == other.value)

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value.toString()
}

open class ErrorResult(
    open val code: ErrorCode = ErrorCode.UNDEFINED,
    open val message: String? = null,
    open val throwable: Throwable? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErrorResult

        if (code != other.code) return false
        if (message != other.message) return false
        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (throwable?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String = "ErrorResult(code=$code, message=$message, throwable=$throwable)"
}

/**
 * Wrap a suspending [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorCore] and the [errorMessage].
 */
suspend fun <T : Any> callSafe(
    call: suspend () -> Result<T>,
    errorCore: ErrorCode = ErrorCode.UNDEFINED,
    errorMessage: String
): Result<T> {
    return try {
        call()
    } catch (e: Throwable) {
        Result.Error(ErrorResult(errorCore, errorMessage, e))
    }
}

/**
 * @since 0.4.0
 * @see callSafe
 */
suspend fun <T : Any> callSafe(
    call: suspend () -> Result<T>,
    errorCode: ErrorCode = ErrorCode.UNDEFINED,
    lazyMessage: () -> Any
) = callSafe(call, errorCode, lazyMessage().toString())

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

