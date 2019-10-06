package cz.eman.kaal.presentation.lifecycle.channels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import cz.eman.logger.logDebug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.whileSelect
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 *  Transfer [ReceiveChannel] to Lifecycle aware [ReceiveChannel]
 */
fun <T> ReceiveChannel<T>.withLifecycle(
    lifecycleOwner: LifecycleOwner,
    scope: CoroutineScope,
    context: CoroutineContext = Unconfined,
    capacity: Int = Channel.CONFLATED
): ReceiveChannel<T> = scope.produce(context, capacity) {
    lifecycleOwner.lifecycle.currentState != Lifecycle.State.DESTROYED || return@produce

    // Create LifecycleEventChannel for observing lifecycle updates
    val lifecycleEventChannel = withContext(Main) { LifecycleEventChannel(lifecycleOwner, Channel.CONFLATED, scope) }
    // Source data channel
    val sourceData = this@withLifecycle
    // Combined channel emitting paired value of latest lifecycle event and source data value
    val combined = combineLatest(channelA = sourceData, channelB = lifecycleEventChannel, scope = scope)

    // Caching latest value to be sure that every value is triggered only once
    var latestDispatched: T? = null
    combined.consumeEach { (value, event) ->
        logDebug { "Channel combined value: [$value, $event]" }

        // Only new value is send when latest lifecycle Event is active Event
        if (value !== latestDispatched && isActiveLifecycleEvent(event)) {
            // Cache latest sent value
            latestDispatched = value
            send(value)
        }
    }
}

/**
 * Combine two [ReceiveChannel] and create one [ReceiveChannel] emitting [Pair] value of the latest
 * values.
 *
 * @param channelA - [ReceiveChannel] emitting first value of [Pair]
 * @param channelB - [ReceiveChannel] emitting second value of [Pair]
 * @param scope - [CoroutineScope]
 * @param context - [CoroutineContext]
 * @param capacity - channel capacity
 */
fun <A, B> combineLatest(
    channelA: ReceiveChannel<A>,
    channelB: ReceiveChannel<B>,
    scope: CoroutineScope,
    context: CoroutineContext = Dispatchers.Unconfined,
    capacity: Int = Channel.UNLIMITED
): ReceiveChannel<Pair<A, B>> {
    var latestA: A? = null
    var latestB: B? = null
    return scope.produce(context, capacity) {
        whileSelect {
            channelA.onReceiveOrNull {
                // If return null - channel is closed [isClosedForReceive] so finish whileSelect
                it != null || return@onReceiveOrNull false

                latestA = it
                bothNotNull(latestA, latestB) { a, b ->
                    send(Pair(a, b))
                }
                return@onReceiveOrNull true
            }
            channelB.onReceiveOrNull {
                // If return null - channel is closed [isClosedForReceive] so finish whileSelect
                it != null || return@onReceiveOrNull false

                latestB = it
                bothNotNull(latestA, latestB) { a, b ->
                    send(Pair(a, b))
                }
                return@onReceiveOrNull true
            }
        }
    }
}

/**
 * Check if [Lifecycle.Event] indicates active state
 *
 * @return true when event indicates active state
 */
fun isActiveLifecycleEvent(event: Lifecycle.Event): Boolean {
    return when (event) {
        Lifecycle.Event.ON_CREATE,
        Lifecycle.Event.ON_RESUME,
        Lifecycle.Event.ON_START -> true
        else -> false
    }
}

/**
 * Inline check that A and B value are not null
 */
inline fun <A, B> bothNotNull(a: A?, b: B?, action: (A, B) -> Unit) {
    if (a != null && b != null) {
        action(a, b)
    }
}
