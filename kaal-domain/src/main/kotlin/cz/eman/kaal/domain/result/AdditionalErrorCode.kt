package cz.eman.kaal.domain.result

/**
 * The enum defines additional error codes which are non-HTTP codes specific to this library (ex: [UNKNOWN_HOST]).
 *
 * @author eMan a.s.
 * @since 0.9.0
 */
@Suppress("unused")
enum class AdditionalErrorCode(override val value: Int) : ErrorCode {

    // Custom errors
    UNKNOWN_HOST(998),
    SOCKET_TIMEOUT(999);

    companion object {
        /**
         * Returns the enum constant of this type with the specified [value].
         *
         * @param value Integer representation of the error status
         */
        fun valueOf(value: Int): ErrorCode? = values().find { it.value == value }
    }
}
