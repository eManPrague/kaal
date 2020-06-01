package cz.eman.kaal.domain.connectivity.model

/**
 * @author eMan a.s.
 */
data class NetworkConnectionInfo(val state: NetworkConnectionState)

/**
 * @author eMan a.s.
 */
sealed class NetworkConnectionState {

    data class Connected(val type: ConnectionType, val isFastConnection: Boolean) :
        NetworkConnectionState()

    object Disconnected : NetworkConnectionState()
}

/**
 * @author eMan a.s.
 */
sealed class ConnectionType {

    object Mobile : ConnectionType()
    object Wifi : ConnectionType()
}
