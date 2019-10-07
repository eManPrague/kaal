package cz.eman.kaal.infrastructure.common

import cz.eman.kaal.domain.exception.EmptyBodyException
import cz.eman.kaal.domain.result.ApiErrorResult
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.HttpStatusErrorCode
import cz.eman.kaal.domain.result.Result
import cz.eman.logger.logError
import retrofit2.Response

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @since 0.4.0
 */
suspend fun <Dto, T> callResult(
    responseCall: suspend () -> Response<Dto>,
    errorMessage: () -> String?,
    map: (Dto) -> T
): Result<T> {
    try {
        val response = responseCall()
        if (response.isSuccessful) {
            val body = response.body()
            return if (body != null) {
                Result.success((map(body)))
            } else {
                errorResult(errorMessage() ?: "Body is null!!!", EmptyBodyException())
            }
        }
        return errorApiResult(response)
    } catch (e: Exception) {
        return errorResult(message = errorMessage() ?: e.message ?: e.toString(), throwable = e)
    }
}

/**
 * @since 0.4.0
 */
fun <Dto, T> errorApiResult(response: Response<Dto>): Result<T> {
    response.logError("${response.code()} ${response.message()}")
    return Result.error(
        error = ApiErrorResult(
            code = HttpStatusErrorCode.valueOf(response.code()),
            message = response.message()
        )
    )
}

/**
 * @since 0.4.0
 */
fun <T> errorResult(message: String, throwable: Throwable?): Result<T> {
    message.logError(message)
    return Result.error(error = ErrorResult(message = message, throwable = throwable))
}
