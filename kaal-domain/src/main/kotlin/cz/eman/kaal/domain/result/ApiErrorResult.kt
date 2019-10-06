package cz.eman.kaal.domain.result

/**
 *
 * @author Roman Holomek <roman.holomek@eman.cz>
 */
data class ApiErrorResult(
    val errorCode: ErrorCode,
    val errorMessage: String? = null,
    val apiThrowable: Throwable? = null
) : ErrorResult(errorCode, errorMessage, apiThrowable)
