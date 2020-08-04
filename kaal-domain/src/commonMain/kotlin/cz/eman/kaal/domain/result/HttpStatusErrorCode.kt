package cz.eman.kaal.domain.result

/**
 * The enum defines standard HTTP status codes which represent client or server error.
 * The codes are defined according to [Internet standard](https://www.ietf.org/assignments/http-status-codes/http-status-codes.xml).
 *
 * The types of error:
 * - 4xx: Client Error - The request contains bad syntax or cannot be fulfilled
 * - 5xx: Server Error - The server failed to fulfill an apparently valid request
 *
 * @author [Filip Šmíd](mailto:filip.smid@eman.cz)
 * @since 0.4.0
 */
@Suppress("unused")
enum class HttpStatusErrorCode(override val value: Int) : ErrorCode {
    // 4xx Client Error
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PAYMENT_REQUIRED(402),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    NOT_ACCEPTABLE(406),
    PROXY_AUTHENTICATION_REQUIRED(407),
    REQUEST_TIMEOUT(408),
    CONFLICT(409),
    GONE(410),
    LENGTH_REQUIRED(411),
    PRECONDITION_FAILED(412),
    PAYLOAD_TOO_LARGE(413),
    URI_TOO_LONG(414),
    UNSUPPORTED_MEDIA_TYPE(415),
    RANGE_NOT_SATISFIABLE(416),
    EXPECTATION_FAILED(417),
    MISDIRECTED_REQUEST(421),
    UNPROCESSABLE_ENTITY(422),
    LOCKED(423),
    FAILED_DEPENDENCY(424),
    TOO_EARLY(425),
    UPGRADE_REQUIRED(426),
    PRECONDITION_REQUIRED(428),
    TOO_MANY_REQUESTS(429),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431),
    UNAVAILABLE_FOR_LEGAL_REASONS(451),

    // 5xx Server Error
    INTERNAL_SERVER_ERROR(500),
    NOT_IMPLEMENTED(501),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504),
    HTTP_VERSION_NOT_SUPPORTED(505),
    VARIANT_ALSO_NEGOTIATES(506),
    INSUFFICIENT_STORAGE(507),
    LOOP_DETECTED(508),
    NOT_EXTENDED(510),
    NETWORK_AUTHENTICATION_REQUIRED(511);

    companion object {
        /**
         * Returns the enum constant of this type with the specified [value].
         *
         * @param value Integer representation of the error status
         *
         * @throws IllegalArgumentException If this enum type has no constant with the specified [value].
         */
        fun valueOf(value: Int) = values().find { it.value == value }
            ?: throw IllegalArgumentException("Unknown HTTP error status code: $value")
    }
}
