package cz.eman.kaal.domain.addon.error

import cz.eman.kaal.domain.result.ErrorResult

/**
 * @author eMan s.r.o.
 */
data class AddonErrorResult(
    override val code: AddonErrorCode,
    override val message: String? = null,
    override val throwable: Throwable? = null
) : ErrorResult(code, message, throwable)
