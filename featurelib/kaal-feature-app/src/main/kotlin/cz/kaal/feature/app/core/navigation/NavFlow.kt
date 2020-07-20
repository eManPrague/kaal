package cz.kaal.feature.app.core.navigation

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointParamKey
import cz.kaal.feature.app.core.feature.FeatureApplication

/**
 * Feature flow interface which contains a main functionality to make feature flow working.
 *
 * @author eMan a.s.
 * @since 1.0.0
 */
abstract class NavFlow {

    private var navController: NavController? = null

    /**
     * Bind current [NavController] to Feature flow which using it for navigation to destinations
     * @param[navController] the current navigation controller which is used by a flow to navigate to destinations/
     */
    fun bind(navController: NavController) {
        this.navController = navController
    }

    /**
     * Flow is not used anymore, so [NavController] could be unbind.
     */
    fun unbind() {
        navController = null
    }

    /**
     * Simple way to run [AddonApplication] from any [FeatureApplication]
     * TODO extend by actionId and custom parameters
     */
    fun runAddon(
        entryPoint: EntryPointInfo,
        navArgs: Array<out Pair<EntryPointParamKey, Any?>>?
    ) {
        navController?.let { navigationController ->
            val graph = navigationController.navInflater.inflate(entryPoint.navGraphRef)

            if (entryPoint.actionId != INVALID_START_DESTINATION) {
                graph.startDestination = entryPoint.actionId
            }

            navigationController.graph.addDestination(graph)

            navigationController.navigate(
                graph.id,
                navArgs?.let {
                    bundleOf(*(navArgs.map { it.first.keyName to it.second }.toTypedArray()))
                }
            )
        }
    }

    protected fun navigateTo(route: String) {
        navController?.navigate(Uri.parse(route))
    }

    companion object {
        private const val INVALID_START_DESTINATION = 0
    }
}
