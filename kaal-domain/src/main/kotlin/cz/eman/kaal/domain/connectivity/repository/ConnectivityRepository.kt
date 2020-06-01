package cz.eman.kaal.domain.connectivity.repository

/**
 * @author eMan a.s.
 */
interface ConnectivityRepository {

    fun isOnline(): Boolean

    fun isConnectionFast(): Boolean

    fun isOnWifi(): Boolean
}
