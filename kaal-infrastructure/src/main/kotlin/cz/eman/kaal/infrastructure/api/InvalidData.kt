package cz.eman.kaal.infrastructure.api

import cz.eman.kaal.domain.result.ErrorResult

/**
 * Class informing that data was invalid.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 0.9.0
 */
data class InvalidData(val errorResult: ErrorResult) : Exception()
