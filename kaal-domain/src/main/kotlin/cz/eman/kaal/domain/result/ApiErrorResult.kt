package cz.eman.kaal.domain.result

/**
 *
 * @author Roman Holomek <roman.holomek@eman.cz>
 */
data class ApiErrorResult(val code: Int,
                          val errorMessage: String? = null,
                          val apiThrowable: Throwable? = null) : ErrorResult(errorMessage, apiThrowable)