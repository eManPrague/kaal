package cz.eman.kaal.domain.connectivity.usecase

import cz.eman.kaal.domain.connectivity.model.ConnectionType
import cz.eman.kaal.domain.connectivity.model.NetworkConnectionInfo
import cz.eman.kaal.domain.connectivity.model.NetworkConnectionState
import cz.eman.kaal.domain.connectivity.repository.ConnectivityRepository
import cz.eman.kaal.domain.usecases.UseCaseNoParams

/**
 * @author eMan a.s.
 */
class GetConnectivityInfoUseCase(private val connectivityRepository: ConnectivityRepository) :
    UseCaseNoParams<NetworkConnectionInfo>() {

    override suspend fun doWork(params: Unit): NetworkConnectionInfo {
        val connectionState = if (connectivityRepository.isOnline()) {
            NetworkConnectionState.Connected(
                type = getConnectionType(),
                isFastConnection = connectivityRepository.isConnectionFast()
            )
        } else {
            NetworkConnectionState.Disconnected
        }

        return NetworkConnectionInfo(state = connectionState)
    }

    private fun getConnectionType() = if (connectivityRepository.isOnWifi()) {
        ConnectionType.Wifi
    } else {
        ConnectionType.Mobile
    }
}
