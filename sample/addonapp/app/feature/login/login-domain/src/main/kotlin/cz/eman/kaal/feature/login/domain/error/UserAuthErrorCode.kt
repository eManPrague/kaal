package cz.eman.kaal.feature.login.domain.error

import cz.eman.kaal.domain.result.ErrorCode

/**
 * Enum defines all common error codes across the Multibanking process
 *
 * @author eMan a.s.
 * @see[ErrorCode]
 */
enum class UserAuthErrorCode(override val value: Int) : ErrorCode {

    INVALID_USER_CREDENTIALS(1),
    USER_ALREADY_EXIST(2)
}
