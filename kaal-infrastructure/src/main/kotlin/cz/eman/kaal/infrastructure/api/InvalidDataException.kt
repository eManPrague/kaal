package cz.eman.kaal.infrastructure.api

import cz.eman.kaal.domain.result.ErrorResult

/**
 * Exception informing that data was invalid or incomplete. Can be returned by data mapper.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 0.9.0
 */
data class InvalidDataException(val errorResult: ErrorResult) : Exception()
