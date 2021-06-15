package cz.eman.kaal.infrastructure.api.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.HttpStatusErrorCode
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.map
import cz.eman.kaal.infrastructure.api.InvalidData
import cz.eman.logger.logError
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.Optional

/**
 * Implementation of [KaalRetrofitCaller] handling Retrofit2 calls and their results.
 *
 * @author eMan a.s.
 * @since 0.9.0
 */
@Suppress("unused")
open class KaalRetrofitCallerImpl : KaalRetrofitCaller {

    /**
     * Calls a [responseCall] and handles the response using [responseCall]. Uses [map] function to determine if the
     * call was successful and result data is not null. If it is it will throw and catch [IllegalStateException]
     * informing that this call should not be used with empty body calls.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @see responseCall
     * @see handleResponse
     * @see handleCallException
     * @since 0.9.0
     */
    override suspend fun <Dto, T> callResult(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<T> {
        return try {
            handleResponse(responseCall(), map).map { data ->
                data ?: throw IllegalStateException(EMPTY_DATA_EXCEPTION)
            }
        } catch (ex: Exception) {
            handleCallException(ex, errorMessage())
        }
    }

    /**
     * Calls a [responseCall] and handles the response using [responseCall]. Uses [map] function to wrap resulting data
     * in [Optional]. If the data is null then it uses [Optional.empty] to return empty result.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @see responseCall
     * @see handleResponse
     * @see handleCallException
     * @since 0.9.0
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun <Dto, T> callResultOptional(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<Optional<T>> {
        return try {
            handleResponse(responseCall(), map).map { data: T? ->
                if (data != null) {
                    Optional.of(data)
                } else {
                    Optional.empty()
                }
            }
        } catch (ex: Exception) {
            handleCallException(ex, errorMessage())
        }
    }

    /**
     * Calls a [responseCall] and handles the response using [responseCall]. Can return result with nullable [T] object
     * when response was successful but no data was returned.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @see responseCall
     * @see handleResponse
     * @see handleCallException
     * @since 0.9.0
     */
    override suspend fun <Dto, T> callResultNull(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<T?> {
        return try {
            handleResponse(responseCall(), map)
        } catch (ex: Exception) {
            handleCallException(ex, errorMessage())
        }
    }

    /**
     * Creates an error from the [response]. Gets error code from the response and parses it to [HttpStatusErrorCode].
     * Creates [Result.error] using [errorResult] containing code and response message.
     *
     * @param response used to create [Result.Error]
     * @return [Result] with type [T] - always creates [Result.Error]
     * @see errorResult
     */
    open fun <Dto, T> createApiErrorResult(response: Response<Dto>): Result<T> {
        val responseCode = response.code()
        val errorMessage = response.message()
        response.logError("$responseCode $errorMessage")

        return errorResult(
            code = HttpStatusErrorCode.valueOf(responseCode),
            message = errorMessage
        )
    }

    /**
     * Handles call exception. Any exception that is handled is used to create [Result.Error]. It makes sure the call
     * does not crash the app and it receives information about what happened.
     *
     * @param ex Exception to handle
     * @param errorMessage used to modify [Result.Error] message
     * @return [Result] with type [T] - always creates [Result.Error]
     * @see errorResult
     */
    private fun <T> handleCallException(ex: Exception, errorMessage: String?): Result<T> {
        val errorCode = when (ex) {
            is InvalidData -> return Result.error(error = ex.errorResult)
            is SocketTimeoutException -> HttpStatusErrorCode.SOCKET_TIMEOUT
            is UnknownHostException -> HttpStatusErrorCode.UNKNOWN_HOST
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
     */
    private suspend fun <Dto, T> handleResponse(response: Response<Dto>, map: suspend (Dto) -> T): Result<T?> {
        return when {
            response.isSuccessful -> {
                val body = response.body()
                if (body != null) {
                    Result.success(map(body))
                } else {
                    Result.success(null)
                }
            }
            else -> createApiErrorResult(response)
        }
    }

    companion object {
        const val EMPTY_DATA_EXCEPTION = "Body was empty in CallResult. If the API returns empty body use " +
            "CallResultOptional / CallResultNull"
        const val EMPTY_DATA_MESSAGE = "Body was empty"
    }
}
