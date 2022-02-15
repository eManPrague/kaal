package cz.eman.kaal.domain.result

/**
 * Contains 3xx HTTP codes since some libraries may consider them as an error. Some libraries represent success as codes
 * from 200 to 300 and everything else is an error, even though 3XX codes are usually not meant as an error.
 *
 * @author eMan a.s.
 * @since 0.9.0
 */
@Suppress("unused")
enum class RedirectErrorCode(override val value: Int) : ErrorCode {

    // 3xx Redirects (can be considered as error = not success)
    MULTIPLE_CHOICES(300),
    MOVED_PERMANENTLY(301),
    FOUND(302),
    SEE_OTHER(303),
    NOT_MODIFIED(304),
    USE_PROXY(305),
    SWITCH_PROXY(306),
    TEMPORARY_REDIRECT(307);

    companion object {
        /**
         * Returns the enum constant of this type with the specified [value].
         *
         * @param value Integer representation of the error status
         */
        fun valueOf(value: Int): ErrorCode? = values().find { it.value == value }
    }
}
