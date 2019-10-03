package cz.eman.kaal.presentation.lifecycle.channels

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

/**
 * [ReceiveChannel] emitting [Lifecycle.Event] updates
 */
class LifecycleEventChannel private constructor(
    private val lifecycle: Lifecycle,
    private val channel: Channel<Lifecycle.Event>,
    private val scope: CoroutineScope
) : LifecycleObserver, ReceiveChannel<Lifecycle.Event> by channel {

    @MainThread
    constructor(
        lifecycleOwner: LifecycleOwner,
        capacity: Int = Channel.RENDEZVOUS,
        scope: CoroutineScope
    ) : this(lifecycleOwner.lifecycle, Channel(capacity), scope)

    init {
        lifecycle.addObserver(this)
    }

    @AnyThread
    override fun cancel() {
        scope.launch(Main) {
            lifecycle.removeObserver(this@LifecycleEventChannel)
        }
        channel.cancel()
    }

    @MainThread
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(source: LifecycleOwner, event: Lifecycle.Event) {
        check(event != Lifecycle.Event.ON_ANY) { "Invalid event - should not never happen" }

        scope.launch {
            try {
                channel.send(event)
                if (event == Lifecycle.Event.ON_DESTROY) {
                    channel.close()
                }
            } catch (ignore: ClosedSendChannelException) {
                // channel just closed, ignore this
            }
        }

        if (event == Lifecycle.Event.ON_DESTROY) {
            lifecycle.removeObserver(this)
        }
    }
}
