package cz.eman.kaal.domain.action.error

import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
data class ActionErrorResult(
    override val message: String?,
    override val code: ErrorCode
) : ErrorResult(
    message = message
)
