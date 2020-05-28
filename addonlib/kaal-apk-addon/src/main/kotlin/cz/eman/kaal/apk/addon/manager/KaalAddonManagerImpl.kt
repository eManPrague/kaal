package cz.eman.kaal.apk.addon.manager

import cz.eman.kaal.domain.addon.AddonInfo
import cz.eman.kaal.domain.addon.app.AddonApplication
import cz.eman.kaal.domain.addon.error.AddonErrorCode
import cz.eman.kaal.domain.addon.error.AddonErrorResult
import cz.eman.kaal.domain.addon.manifest.entrypoint.action.ActionEntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.ApiEntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo
import cz.eman.kaal.domain.result.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

/**
 *  Addon Manager implementation
 *
 *  @author eMan s.r.o.
 */
abstract class KaalAddonManagerImpl : AddonManager, AddonInfo {

    /**
     * List of registered [AddonApplication]
     */
    private val addons: MutableList<AddonApplication> = mutableListOf()
    private var activeAddonShortcuts: List<ShortcutEntryPointInfo> = listOf()
    private var activeAddonDashBottomShortcuts: List<ShortcutEntryPointInfo> = listOf()
    private var activeAddonDashOthersShortcuts: List<ShortcutEntryPointInfo> = listOf()
    private var activeDemoEndpoints: List<ApiEntryPointInfo> = listOf()

    @ExperimentalCoroutinesApi
    private val addonsChannel = ConflatedBroadcastChannel(addons)

    @ExperimentalCoroutinesApi
    private val shortcutsChannel = ConflatedBroadcastChannel(activeAddonShortcuts)

    @ExperimentalCoroutinesApi
    private val dashBottomShortcutsChannel =
        ConflatedBroadcastChannel(activeAddonDashBottomShortcuts)

    @ExperimentalCoroutinesApi
    private val dashOthersShortcutsChannel =
        ConflatedBroadcastChannel(activeAddonDashOthersShortcuts)

    @ExperimentalCoroutinesApi
    override fun registerAddonApplication(addonApplication: AddonApplication): Boolean {
        if (addons.any {
                it.getManifest().getAddonId() == addonApplication.getManifest().getAddonId()
            }) {
            return false
        }
        addons.add(addonApplication)
        refreshEntryPoints()
        return true
    }

    override fun initAddons() {
        addons.forEach { it.init() }
    }

    @ExperimentalCoroutinesApi
    override fun observeAddonApplications() = addonsChannel.openSubscription()

    @ExperimentalCoroutinesApi
    override fun observeShortcutEntryPoints() = shortcutsChannel.openSubscription()

    @ExperimentalCoroutinesApi
    override fun observeDashboardBottomShortcutEntryPoints() =
        dashBottomShortcutsChannel.openSubscription()

    @ExperimentalCoroutinesApi
    override fun observeDashboardOthersShortcutEntryPoints() =
        dashOthersShortcutsChannel.openSubscription()

    override fun getActionEntryPoints(): List<ActionEntryPointInfo> {
        return getAllActionEntryPoints()
    }

    override fun getDemoApiEntryPointInfo(endpoint: String): Result<ApiEntryPointInfo> {
        return findDemoApiEntryPoint(endpoint)
    }

    private fun findDemoApiEntryPoint(endpoint: String): Result<ApiEntryPointInfo> {
        return if (activeDemoEndpoints.isEmpty()) {
            // TODO logger logError { "No demo addons available yet!" }
            Result.error(
                AddonErrorResult(
                    code = AddonErrorCode.DEMO_API_ADDON_NOT_AVAILABLE,
                    message = "No demo addons available yet!"
                )
            )
        } else {
            val entryPoint = activeDemoEndpoints.firstOrNull {
                endpoint.endsWith(it.endpoint)
            } ?: activeDemoEndpoints.firstOrNull {
                endpoint.contains(it.endpoint)
            } // FIXME - use a regular expression to find demo endpoint
            if (entryPoint != null) {
                Result.success(entryPoint)
            } else {
                // TODO logger in a Kotlin module logError { "No addon found for the endpoint=$endpoint" }
                Result.error(
                    AddonErrorResult(
                        code = AddonErrorCode.DEMO_API_NOT_FOUND,
                        message = "No addon found for the endpoint=$endpoint"
                    )
                )
            }
        }
    }

    @ExperimentalCoroutinesApi
    protected fun refreshEntryPoints() {
        activeAddonShortcuts = getAllShortcutEntryPoints()
        activeAddonDashBottomShortcuts = getAllDashBottomShortcutEntryPoints()
        activeAddonDashOthersShortcuts = getAllDashOthersShortcutEntryPoints()
        activeDemoEndpoints = getAllDemoApiEntryPoints()

        shortcutsChannel.offer(activeAddonShortcuts)
        dashBottomShortcutsChannel.offer(activeAddonDashBottomShortcuts)
        dashOthersShortcutsChannel.offer(activeAddonDashOthersShortcuts)
    }

    private fun getAllShortcutEntryPoints(): List<ShortcutEntryPointInfo> {
        return addons.filter {
            it.getManifest().getEntryPoints().shortcutDefinition() != null
        }.map {
            val definition = it.getManifest().getEntryPoints()
                .shortcutDefinition()
            requireNotNull(definition)
            definition.shortcut
        }
    }

    private fun getAllDashBottomShortcutEntryPoints(): List<ShortcutEntryPointInfo> {
        return addons.filter {
            it.getManifest().getEntryPoints().dashboardDefinition().shortcuts.bottom != null
        }.map {
            val definition =
                it.getManifest().getEntryPoints().dashboardDefinition().shortcuts.bottom
            requireNotNull(definition)
        }
    }

    private fun getAllDashOthersShortcutEntryPoints(): List<ShortcutEntryPointInfo> {
        return addons.filter {
            it.getManifest().getEntryPoints().dashboardDefinition().shortcuts.others.isNotEmpty()
        }.map {
            it.getManifest().getEntryPoints().dashboardDefinition().shortcuts.others
        }.flatten()
    }

    private fun getAllActionEntryPoints(): List<ActionEntryPointInfo> {
        return addons.filter {
            it.getManifest().getEntryPoints().actionDefinition() != null
        }.map {
            val definition = it.getManifest().getEntryPoints().actionDefinition()

            requireNotNull(definition)
            definition.actions
        }.flatten()
    }

    private fun getAllDemoApiEntryPoints(): List<ApiEntryPointInfo> {
        return addons.filter {
            it.getManifest().getEntryPoints().demoDefinition() != null
        }.map {
            val definition = requireNotNull(it.getManifest().getEntryPoints().demoDefinition())
            definition.apiEntryPoints
        }.flatten()
    }
}
