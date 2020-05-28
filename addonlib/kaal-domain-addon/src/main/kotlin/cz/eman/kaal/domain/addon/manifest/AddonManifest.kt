package cz.eman.kaal.domain.addon.manifest

import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointsDefinition

/**
 * Each Addon must extend this class.
 *
 * It provides basic Addon settings, information and serves as Addon gateway (starting point)
 * @author eMan s.r.o.
 */
interface AddonManifest {

    /**
     * Returns Addon textual unique ID
     * @return Addon textual unique ID (usually the Addon package name)
     */
    fun getAddonId(): String

    /**
     * @return Addon's App root screens. May be empty if Addon has no App screens
     */
    fun getEntryPoints(): EntryPointsDefinition
}
