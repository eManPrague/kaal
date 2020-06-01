package cz.eman.kaal.data.connectivity.source

/**
 * @author eMan a.s.
 */
interface ConnectivitySource {

    fun isConnectedFast(): Boolean

    fun isOnline(): Boolean

    fun isOnWifi(): Boolean

    fun isOnMobile(): Boolean
}
