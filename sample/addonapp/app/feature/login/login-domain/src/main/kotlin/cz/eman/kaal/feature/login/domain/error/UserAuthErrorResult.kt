package cz.eman.kaal.feature.login.domain.error

import cz.eman.kaal.domain.result.ErrorResult

/**
 * @author eMan a.s.
 */
data class UserAuthErrorResult(
    override val code: UserAuthErrorCode,
    override val message: String? = null,
    override val throwable: Throwable? = null
) : ErrorResult(code, message, throwable)
