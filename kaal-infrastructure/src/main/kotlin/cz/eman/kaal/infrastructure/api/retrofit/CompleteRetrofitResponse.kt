package cz.eman.kaal.infrastructure.api.retrofit

import retrofit2.Response
import cz.eman.kaal.domain.result.Result

/**
 * Data class containing complete response of the API call. Contains three parts:
 * 1) Kaal [result] with mapped [T] object.
 * 2) Retrofit2 [response] with mapped [Dto] object and additional information.
 * 3) Okhttp3 [httpResponse] with raw call data,
 *
 * @author eMan a.s.
 * @since 0.9.0
 */
data class CompleteRetrofitResponse<T, Dto>(
    val result: Result<T>,
    val response: Response<Dto>?,
    val httpResponse: okhttp3.Response?
)
