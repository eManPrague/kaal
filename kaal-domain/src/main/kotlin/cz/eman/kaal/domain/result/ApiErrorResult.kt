package cz.eman.kaal.domain.result

/**
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 */
data class ApiErrorResult(
    override val code: ErrorCode,
    override val message: String? = null,
    override val throwable: Throwable? = null
) : ErrorResult(code, message, throwable)
