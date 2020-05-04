package cz.eman.kaal.domain.exception

/**
 * Exception that there is an empty body inside of response
 *
 * @author Roman Holomek <roman.holomek@eman.cz>
 * @since 0.4.0
 */
class EmptyBodyException : IOException("Response with empty body")
