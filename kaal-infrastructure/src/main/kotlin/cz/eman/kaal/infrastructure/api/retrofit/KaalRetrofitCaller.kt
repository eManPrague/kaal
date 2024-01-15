package cz.eman.kaal.infrastructure.api.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import cz.eman.kaal.domain.result.AdditionalErrorCode
import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.HttpStatusErrorCode
import cz.eman.kaal.domain.result.RedirectErrorCode
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.map
import cz.eman.kaal.infrastructure.api.InvalidDataException
import cz.eman.logger.logError
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.Optional
import kotlin.reflect.KClass

/**
 * Implementation of API caller handling Retrofit2 calls and their results. Provides multiple call functions based on
 * which result type is required. Most APIs would use [callResult] to get mapped object. But there are also calls that
 * allow ignoring result or return raw response.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 0.9.0
 */
@Suppress("unused")
open class KaalRetrofitCaller {

    /**
     * The list of exceptions to re-throw from [callResult], [callResultOptional] and [callResultCompleteOptional]
     * methods. Could be overridden in implementations to provide a list of exception classes which should be re-thrown.
     * By default list is empty and each exception is wrapped in [Result.Error].
     */
    open val rethrowExceptions: List<KClass<*>> = emptyList()

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to [T] on success else it returns
     * [Result.Error] on any error or exception.
     *
     * Handles response using [handleResponse] which is then mapped using [Result.map] function to determine if the call
     * was successful and result body is not null. If it is it will throw and catch [IllegalStateException] informing
     * that this call should not be used with empty body calls. If any exception occurs it is handled by
     * [handleCallException].
     *
     * Note: This function conciser successful response with null body as an error. If the API should return empty body
     * for example with 204/205/304 codes then use [callResultOptional].
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @return [Result] with [T]
     * @see responseCall
     * @see handleResponse
     * @see handleCallException
     * @since 0.9.0
     */
    suspend fun <Dto, T> callResult(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<T> {
        return try {
            handleResponse(doResponseCall(responseCall), map).map { data ->
                data ?: throw IllegalStateException(EMPTY_DATA_EXCEPTION)
            }
        } catch (ex: Exception) {
            handleCallException(ex, errorMessage())
        }
    }

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to [Optional]<[T]> on success else it
     * returns [Result.Error] on any error or exception. Optional allows support for empty body.
     *
     * Handles response using [handleResponse] and is returned. If any exception occurs it is handled by
     * [handleCallException].
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @return [Result] with [Optional]<[T]> allowing success with empty body
     * @since 0.9.0
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun <Dto, T> callResultOptional(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<Optional<T>> {
        val result: Result<T?> = try {
            handleResponse(doResponseCall(responseCall), map)
        } catch (ex: Exception) {
            handleCallException(ex, errorMessage())
        }
        return result.map { Optional.ofNullable(it) }
    }

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to [Optional]<[T]> on success else it
     * returns [Result.Error] on any error or exception. Optional allows support for empty body. Result of this function
     * is a [Pair] of [Result] and [Response] so you can easily access any response metadata you need (like headers,
     * result code and others provided by Retrofit2).
     *
     * Handles response using [handleResponse], paired up with response and then returned. If any exception occurs it is
     * handled by [handleCallException] and paired to response (if possible).
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @return [CompleteRetrofitResponse] with [T] (Optional) and [Dto] object
     * @see responseCall
     * @see handleResponse
     * @see handleCallException
     * @since 0.9.0
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun <Dto, T> callResultCompleteOptional(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): CompleteRetrofitResponse<Optional<T>, Dto> {
        var response: Response<Dto>? = null
        val result = try {
            response = doResponseCall(responseCall)
            handleResponse(response, map)
        } catch (ex: Exception) {
            handleCallException<T>(ex, errorMessage())
        }
        return CompleteRetrofitResponse(result.map { Optional.ofNullable(it) }, response, response?.raw())
    }

    /**
     * Does the actual response call. Runs the call and returns the response.
     *
     * @param responseCall Retrofit2 call to handle
     * @return [Response] with type [Dto]
     * @since 0.9.2
     */
    open suspend fun <Dto> doResponseCall(
        responseCall: suspend () -> Response<Dto>
    ): Response<Dto> = responseCall()

    /**
     * Creates an error from the [response]. Gets error code from the response and parses it to [HttpStatusErrorCode].
     * Creates [Result.error] using [errorResult] containing code and response message.
     *
     * @param response used to create [Result.Error]
     * @return [Result] with type [T] - always creates [Result.Error]
     * @see errorResult
     * @since 0.9.0
     */
    open fun <Dto, T> createApiErrorResult(response: Response<Dto>): Result<T> {
        val responseCode = response.code()
        val errorMessage = response.message()
        response.logError("$responseCode $errorMessage")

        return errorResult(
            code = HttpStatusErrorCode.valueOf(responseCode) ?: RedirectErrorCode.valueOf(responseCode)
            ?: ErrorCode.UNDEFINED,
            message = errorMessage
        )
    }

    /**
     * Creates [Result.Error] using function parameters. It also logs the error to be visible in the log (uses Timber).
     *
     * @param code error code
     * @param message containing error information
     * @param throwable containing detail (exception) information
     * @since 0.9.0
     */
    fun <T> errorResult(
        code: ErrorCode = ErrorCode.UNDEFINED,
        message: String,
        throwable: Throwable? = null
    ): Result<T> {
        message.logError(message, throwable)
        return Result.error(error = ErrorResult(code = code, message = message, throwable = throwable))
    }

    /**
     * Handles call exception. Any exception that is handled is used to create [Result.Error]. It makes sure the call
     * does not crash the app and it receives information about what happened.
     *
     * Override [rethrowExceptions] to specify the list of exceptions to re-throw.
     *
     * @param ex Exception to handle
     * @param errorMessage used to modify [Result.Error] message
     * @return [Result] with type [T] - always creates [Result.Error]
     * @see errorResult
     * @since 0.9.0
     */
    private fun <T> handleCallException(ex: Exception, errorMessage: String?): Result<T> {
        if (rethrowExceptions.isNotEmpty() && ex::class in rethrowExceptions) {
            throw ex
        }

        val errorCode = when (ex) {
            is InvalidDataException -> return Result.error(error = ex.errorResult)
            is SocketTimeoutException -> AdditionalErrorCode.SOCKET_TIMEOUT
            is UnknownHostException -> AdditionalErrorCode.UNKNOWN_HOST
            else -> ErrorCode.UNDEFINED
        }

        return errorResult(
            code = errorCode,
            message = errorMessage ?: ex.message ?: ex.toString(),
            throwable = ex
        )
    }

    /**
     * Handles response and returns proper [Result]. If the response is successful and body is not null it will use
     * [map] function to map [Dto] object to [T]. If it is null it will create [Result.Success] with null body. If the
     * response is not successful it will create an [createApiErrorResult].
     *
     * @param response to be handled and parsed
     * @param map used to map [Dto] object to [T]
     * @return @return [Result] with type [T] or null
     * @see createApiErrorResult
     * @since 0.9.0
     */
    private suspend fun <Dto, T> handleResponse(response: Response<Dto>, map: suspend (Dto) -> T): Result<T?> {
        return when {
            response.isSuccessful -> {
                response.body()?.let {
                    Result.success(map(it))
                } ?: Result.success(null)
            }
            else -> createApiErrorResult(response)
        }
    }

    companion object {
        const val EMPTY_DATA_EXCEPTION = "Body was empty in CallResult. If the API returns empty body use " +
            "CallResultOptional"
        const val EMPTY_DATA_MESSAGE = "Body was empty"
    }
}
