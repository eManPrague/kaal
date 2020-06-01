package cz.eman.kaal.data.connectivity.repository

import cz.eman.kaal.data.connectivity.source.ConnectivitySource
import cz.eman.kaal.domain.connectivity.repository.ConnectivityRepository

/**
 * @author eMan a.s.
 * @see[ConnectivityRepository]
 */
class ConnectivityRepositoryImpl(private val connectionSource: ConnectivitySource) :
    ConnectivityRepository {

    override fun isOnline() = connectionSource.isOnline()

    override fun isConnectionFast() = connectionSource.isConnectedFast()

    override fun isOnWifi() = connectionSource.isOnWifi()
}
