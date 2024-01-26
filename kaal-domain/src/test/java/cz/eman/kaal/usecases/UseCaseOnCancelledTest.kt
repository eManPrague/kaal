package cz.eman.kaal.usecases

import cz.eman.kaal.domain.coroutines.cancellationAware
import cz.eman.kaal.domain.usecases.UseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import kotlin.coroutines.coroutineContext

@ExperimentalCoroutinesApi
class UseCaseOnCancelledTest {

    @Test
    fun `The 'onCancelled' method is called when scope is cancelled`() {
        val target = TestUseCase()
        TestCoroutineScope(SupervisorJob()).apply {
            launch {
                target.invoke("params")
            }
            cancel()
        }
        assertEquals(1, target.onCancelledCalls.size)
        assertEquals("params", target.onCancelledCalls.first())
    }

    @Test
    fun `The job is still active in 'onCancelled' method`() {
        val target = TestUseCase(
            onCancelled = {
                assertTrue(coroutineContext.isActive)
            }
        )
        TestCoroutineScope(SupervisorJob()).apply {
            launch {
                target.invoke("params")
            }
            cancel()
        }
    }

    @Test
    fun `The 'onCancelled' method is not called by default`() = runBlockingTest {
        val target = TestUseCase()
        target.invoke("params")
        assertEquals(0, target.onCancelledCalls.size)
    }

    private class TestUseCase(private val onCancelled: suspend () -> Unit = {}) : UseCase<Unit, String>() {

        val onCancelledCalls: MutableList<String> = mutableListOf()

        override suspend fun doWork(params: String): Unit = cancellationAware(
            onCancelled = {
                onCancelled.invoke()
                onCancelledCalls.add(params)
            }
        ) {
            delay(1_000L)
        }
    }
}
