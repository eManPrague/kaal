package cz.eman.kaal.apk.addon.manager

import cz.eman.kaal.domain.addon.app.AddonApplication
import kotlinx.coroutines.channels.ReceiveChannel

/**
 *  Addon Manager
 *
 *  @author eMan s.r.o.
 */
interface AddonManager {

    /**
     * Register [AddonApplication]
     */
    fun registerAddonApplication(addonApplication: AddonApplication): Boolean

    /**
     * Will call init() on  all registered [AddonApplication]
     */
    fun initAddons()

    /**
     * Observe all registered [AddonApplication]
     */
    fun observeAddonApplications(): ReceiveChannel<List<AddonApplication>>
}
