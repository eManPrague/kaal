package cz.eman.kaal.domain.result

/**
 *
 * @author Roman Holomek <roman.holomek@eman.cz>
 */
data class ApiErrorResult(
    override val code: ErrorCode,
    override val message: String? = null,
    override val throwable: Throwable? = null
) : ErrorResult(code, message, throwable)
