package cz.eman.kaal.domain.addon.app

import cz.eman.kaal.domain.addon.manifest.AddonManifest

/**
 * Addon Application
 *
 * @author eMan s.r.o.
 */
interface AddonApplication {

    /**
     * Get [AddonManifest] for this [AddonApplication]
     *
     * @return [AddonManifest]
     */
    fun getManifest(): AddonManifest

    /**
     * Initial configuration of AddonApplication like DI etc.
     */
    fun init()
}
