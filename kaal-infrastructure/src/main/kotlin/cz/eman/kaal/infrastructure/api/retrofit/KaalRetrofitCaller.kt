package cz.eman.kaal.infrastructure.api.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.map
import cz.eman.logger.logError
import retrofit2.Response
import java.util.Optional

/**
 * Interface for Kaal Retrofit2 calls providing functions which it can handle.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 0.9.0
 */
@Suppress("unused")
interface KaalRetrofitCaller {

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to [T] on success else it returns
     * [Result.Error] on any error or exception.
     *
     * Note: This function conciser successful response with null body as an error. If the API should return empty body
     * for example with 204/205/304 codes then use [callResultOptional], [callResultNull].
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @return [Result] with [T]
     * @since 0.9.0
     */
    suspend fun <Dto, T> callResult(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<T>

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to [Optional]<[T]> on success else it
     * returns [Result.Error] on any error or exception. Optional allows support for empty body.
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
    ): Result<Optional<T>>

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to [Unit] on success else it returns
     * [Result.Error] on any error or exception. [Unit] allows ignoring the actual result and only check for success.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [Unit] object (kept to be used for logging or other functions)
     * @return [Result] with [Unit]
     * @since 0.9.0
     */
    suspend fun <Dto> callResultUnit(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> Unit = { }
    ): Result<Unit> = callResultNull(responseCall, errorMessage, map).map { }

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to nullable [T] on success else it
     * returns [Result.Error] on any error or exception. Optional allows support for empty body.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @return [Result] with [T]? (nullable) allowing success with empty body
     * @since 0.9.0
     */
    suspend fun <Dto, T> callResultNull(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<T?>

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to [Optional]<[T]> on success else it
     * returns [Result.Error] on any error or exception. Optional allows support for empty body. Result of this function
     * is a [Pair] of [Result] and [Response] so you can easily access any response metadata you need (like headers,
     * result code and others provided by Retrofit2).
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @return [Pair] of [Result] with [T] data to [Response] with [Dto] object
     * @since 0.9.0
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun <Dto, T> callResultResponseOptional(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Pair<Result<Optional<T>>, Response<Dto>?>

    /**
     * Calls a [responseCall] and handles the result by mapping the [Dto] object to nullable [T] on success else it
     * returns [Result.Error] on any error or exception. Optional allows support for empty body. Nullable allows support
     * for empty body. Result of this function is a [Pair] of [Result] and [Response] so you can easily access any
     * response metadata you need (like headers, result code and others provided by Retrofit2).
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @return [Pair] of [Result] with [T] data to [Response] with [Dto] object
     * @since 0.9.0
     */
    suspend fun <Dto, T> callResultResponseNull(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Pair<Result<T?>, Response<Dto>?>

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
}
