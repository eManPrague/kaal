package cz.kaal.feature.app.core.navigation

import androidx.navigation.NavController
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointParamKey
import cz.kaal.feature.app.core.feature.FeatureApplication

/**
 * Feature flow interface which contains a main functionality to make feature flow working.
 * @author eMan a.s.
 * @since 1.0.0
 */
interface NavFlow {

    /**
     * Bind current [NavController] to Feature flow which using it for navigation to destinations
     * @param[navController] the current navigation controller which is used by a flow to navigate to destinations/
     */
    fun bind(navController: NavController)

    /**
     * Flow is not used anymore, so [NavController] could be unbind.
     */
    fun unbind()

    /**
     * Simple way to run [AddonApplication] from any [FeatureApplication]
     * TODO extend by actionId and custom parameters
     */
    fun runAddon(
        entryPoint: EntryPointInfo,
        navArgs: Array<out Pair<EntryPointParamKey, Any?>>? = null
    )
}
