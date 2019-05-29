package cz.eman.kaal.presentation.lifecycle.channels

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.coroutineContext

/**
 * Simple way to start observing data with Lifecycle aware [ReceiveChannel]
 */
suspend fun <T> LifecycleOwner.observeData(channel: ReceiveChannel<T>, body: (T) -> Unit) {
    channel.withLifecycle(
            lifecycleOwner = this@observeData,
            scope = CoroutineScope(context = coroutineContext)).consumeEach {
        body(it)
    }
}