package cz.eman.kaal.domain.exception

import java.io.IOException

/**
 * Exception that there is an empty body inside of response
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 1.0.0
 */
class EmptyBodyException : IOException("Response with empty body")
