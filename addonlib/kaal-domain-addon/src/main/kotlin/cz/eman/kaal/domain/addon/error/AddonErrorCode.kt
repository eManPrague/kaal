package cz.eman.kaal.domain.addon.error

import cz.eman.kaal.domain.result.ErrorCode

/**
 * Enum defines all error codes across the Current Account feature
 *
 * @author eMan s.r.o.
 * @see[ErrorCode]
 */
enum class AddonErrorCode(override val value: Int) : ErrorCode {

    DEMO_API_ADDON_NOT_AVAILABLE(1),
    DEMO_API_NOT_FOUND(2)
}
