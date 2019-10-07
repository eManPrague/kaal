package cz.eman.kaal.infrastructure.coroutines.channels

import androidx.lifecycle.*
import kotlinx.coroutines.channels.Channel

/**
 * Class for managing data flow from [LiveData] to [Channel] and closing operations
 *
 * @author Roman Holomek <roman.holomek@eman.cz>
 */
class LiveDataChannel<T>(private val liveData: LiveData<T>) : Observer<T?>, LifecycleObserver {

    val channel = Channel<T?>(Channel.UNLIMITED).apply {
        invokeOnClose { closeLiveData() }
    }

    private fun close() {
        closeChannel()
        closeLiveData()
    }

    private fun closeChannel() {
        channel.close(null)
    }

    private fun closeLiveData() {
        liveData.removeObserver(this)
    }

    override fun onChanged(t: T?) {
        channel.offer(t)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = close()
}
