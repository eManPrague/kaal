package cz.eman.kaal.infrastructure.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import cz.eman.kaal.data.connectivity.source.ConnectivitySource

/**
 * @author eMan a.s.
 * @see[ConnectivitySource]
 */
class DeviceConnectivitySource(private val appContext: Context) :
    ConnectivitySource {

    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    @Suppress("DEPRECATION")
    override fun isOnline(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected
    }

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return
     */
    @Suppress("DEPRECATION")
    override fun isOnWifi(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    @Suppress("DEPRECATION")
    override fun isOnMobile(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
    }

    @Suppress("DEPRECATION")
    override fun isConnectedFast(): Boolean {
        val info = getNetworkInfo() ?: return false
        return info.isConnected && isConnectionFast(type = info.type, subType = info.subtype)
    }

    /**
     * Check if the connection is fast
     * @param type
     * @param subType
     * @return
     */
    @Suppress("DEPRECATION")
    private fun isConnectionFast(type: Int, subType: Int): Boolean {
        return when (type) {
            ConnectivityManager.TYPE_WIFI -> true
            ConnectivityManager.TYPE_MOBILE -> when (subType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_CDMA -> false // ~ 14-64 kbps
                TelephonyManager.NETWORK_TYPE_EDGE -> false // ~ 50-100 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> true // ~ 400-1000 kbps
                TelephonyManager.NETWORK_TYPE_EVDO_A -> true // ~ 600-1400 kbps
                TelephonyManager.NETWORK_TYPE_GPRS -> false // ~ 100 kbps
                TelephonyManager.NETWORK_TYPE_HSDPA -> true // ~ 2-14 Mbps
                TelephonyManager.NETWORK_TYPE_HSPA -> true // ~ 700-1700 kbps
                TelephonyManager.NETWORK_TYPE_HSUPA -> true // ~ 1-23 Mbps
                TelephonyManager.NETWORK_TYPE_UMTS -> true // ~ 400-7000 kbps

                TelephonyManager.NETWORK_TYPE_EHRPD -> true // ~ 1-2 Mbps (API level 13)
                TelephonyManager.NETWORK_TYPE_EVDO_B -> true // ~ 5 Mbps (API level 9)
                TelephonyManager.NETWORK_TYPE_HSPAP -> true // ~ 10-20 Mbps (API level 13)
                TelephonyManager.NETWORK_TYPE_IDEN -> false // ~25 kbps (API level 8)
                TelephonyManager.NETWORK_TYPE_LTE -> true // ~ 10+ Mbps (API level 11)
                19 -> true // NETWORK_TYPE_LTE_CA is hide
                TelephonyManager.NETWORK_TYPE_IWLAN -> true

                // Unknown
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> false
                else -> false
            }
            else -> false
        }
    }

    private fun createNetworkReceiver(
        callback: (Boolean) -> Unit
    ): BroadcastReceiver {
        return (object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                context?.let { callback(isOnline()) } ?: callback(false)
            }
        })
    }

    @Suppress("DEPRECATION")
    private fun getNetworkInfo(): android.net.NetworkInfo? {
        val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo
    }
}
