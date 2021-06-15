package cz.eman.kaal.infrastructure.api.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.logger.logError
import retrofit2.Response
import java.util.Optional

/**
 * Interface for Kaal retrofit2 calls providing functions which it can handle.
 *
 * @author eMan a.s.
 * @since 0.9.0
 */
interface KaalRetrofitCaller {

    /**
     * Calls a [responseCall] and handles the response using [responseCall]. Uses [map] function to determine if the
     * call was successful and result data is not null. If it is it will throw and catch [IllegalStateException]
     * informing that this call should not be used with empty body calls.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @since 0.9.0
     */
    suspend fun <Dto, T> callResult(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<T>

    /**
     * Calls a [responseCall] and handles the response using [responseCall]. Uses [map] function to wrap resulting data
     * in [Optional]. If the data is null then it uses [Optional.empty] to return empty result.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @since 0.9.0
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun <Dto, T> callResultOptional(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<Optional<T>>

    /**
     * Calls a [responseCall] and handles the response using [responseCall]. Can return result with nullable [T] object
     * when response was successful but no data was returned.
     *
     * @param responseCall Retrofit2 call to handle
     * @param errorMessage used to modify error message
     * @param map function mapping [Dto] object to [T] object
     * @see responseCall
     * @since 0.9.0
     */
    suspend fun <Dto, T> callResultNull(
        responseCall: suspend () -> Response<Dto>,
        errorMessage: () -> String?,
        map: suspend (Dto) -> T
    ): Result<T?>

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
