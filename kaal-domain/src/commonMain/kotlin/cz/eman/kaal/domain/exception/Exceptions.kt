package cz.eman.kaal.domain.exception

/**
 * Signals that an I/O exception of some sort has occurred. This
 * class is the general class of exceptions produced by failed or
 * interrupted I/O operations.
 *
 * @since 0.7.0
 */
expect open class IOException(message: String, cause: Throwable?) : Exception {
    
    constructor(message: String)
}
