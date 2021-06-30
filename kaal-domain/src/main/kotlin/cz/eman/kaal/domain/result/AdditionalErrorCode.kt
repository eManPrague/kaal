package cz.eman.kaal.domain.result

/**
 * The enum defines additional error codes. Contains 3xx HTTP codes since some libraries may consider them as an error.
 * Other additional codes are non-HTTP codes specific to this library (ex: [UNKNOWN_HOST]).
 *
 * The types of error:
 * - 3xx: Redirects - Can be considered as error = not success, some libraries (ex.: Retrofit2 does not conciser 300 as
 *   success).
 * - Additional - Custom non http codes.
 *
 * @author eMan a.s.
 * @since 0.9.0
 */
@Suppress("unused")
enum class AdditionalErrorCode(override val value: Int) : ErrorCode {

    // 3xx Redirects (can be considered as error = not success)
    MULTIPLE_CHOICES(300),
    MOVED_PERMANENTLY(301),
    FOUND(302),
    SEE_OTHER(303),
    NOT_MODIFIED(304),
    USE_PROXY(305),
    SWITCH_PROXY(306),
    TEMPORARY_REDIRECT(307),

    // Custom errors
    UNKNOWN_HOST(1000),
    SOCKET_TIMEOUT(1001);

    companion object {
        /**
         * Returns the enum constant of this type with the specified [value].
         *
         * @param value Integer representation of the error status
         */
        fun valueOf(value: Int) = values().find { it.value == value } ?: ErrorCode.UNDEFINED
    }
}
