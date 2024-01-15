package cz.eman.kaal

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.infrastructure.api.retrofit.KaalRetrofitCaller
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.reflect.KClass

@ExperimentalCoroutinesApi
class KaalRetrofitCallerCancellationExceptionTest {

    @Test
    fun `By default KaalRetrofitCaller does not re-throw CancellationException`() = runBlockingTest {
        val caller = KaalRetrofitCaller()
        val result = caller.testCallResult()

        assertTrue(result.isError())
        assertTrue((result as Result.Error).error.throwable is CancellationException)
    }

    @Test
    fun `KaalRetrofitCaller does not re-throw CancellationException when re-throw flag is false`() = runBlockingTest {
        val caller = TestRetrofitCaller(rethrowCancellationException = false)
        val result = caller.testCallResult()

        assertTrue(result.isError())
        assertTrue((result as Result.Error).error.throwable is CancellationException)
    }

    @Test(expected = CancellationException::class)
    fun `KaalRetrofitCaller re-throws CancellationException when re-throw flag is true`() = runBlockingTest {
        val caller = TestRetrofitCaller(rethrowCancellationException = true)
        caller.testCallResult()
    }

    private suspend fun KaalRetrofitCaller.testCallResult(): Result<Any> =
        callResult<Nothing, Any>(
            responseCall = { throw CancellationException() },
            errorMessage = { "Some error message" },
            map = { "Some domain instance" }
        )

    private class TestRetrofitCaller(rethrowCancellationException: Boolean) : KaalRetrofitCaller() {
        override val rethrowExceptions: List<KClass<*>> =
            if (rethrowCancellationException) {
                listOf(CancellationException::class)
            } else {
                emptyList()
            }
    }
}
