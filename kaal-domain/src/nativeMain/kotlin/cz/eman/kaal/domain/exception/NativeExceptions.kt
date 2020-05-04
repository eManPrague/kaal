package cz.eman.kaal.domain.exception

actual open class IOException actual constructor(message: String, cause: Throwable?) : Exception(message, cause) {
    actual constructor(message: String) : this(message, null)
}
