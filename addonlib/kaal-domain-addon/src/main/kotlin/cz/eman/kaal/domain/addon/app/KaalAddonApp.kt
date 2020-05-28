package cz.eman.kaal.domain.addon.app

import cz.eman.kaal.domain.addon.AddonInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointParamKey

/**
 * @author eMan (vaclav.souhrada@eman.cz)
 */
interface KaalAddonApp {

    /**
     * Register [AddonApplication]
     *
     * @return false when registered already
     */
    fun registerAddonApplication(addonApplication: AddonApplication): Boolean

    /**
     * Get [AddonInfo]
     */
    fun getAddonInfo(): AddonInfo

    /**
     * Run [AddonApplication]
     */
    fun runAddon(
        entryPoint: EntryPointInfo,
        data: Array<out Pair<EntryPointParamKey, Any?>> = emptyArray()
    )
}
