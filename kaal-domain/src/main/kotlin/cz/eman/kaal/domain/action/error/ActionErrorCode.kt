package cz.eman.kaal.domain.action.error

import cz.eman.kaal.domain.result.ErrorCode

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
enum class ActionErrorCode(override val value: Int) : ErrorCode {

    NO_SUPPORTING_MODULE_REGISTERED(1),
    NO_ACTION_SUCCEED(2)
}
