package cz.eman.kaal.domain.addon

import cz.eman.kaal.domain.addon.manifest.entrypoint.action.ActionEntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.ApiEntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo
import cz.eman.kaal.domain.result.Result
import kotlinx.coroutines.channels.ReceiveChannel

/**
 *  Addon info
 *
 *  @author eMan s.r.o.
 */
interface AddonInfo {

    /**
     * Observe all [ShortcutEntryPointInfo]
     */
    fun observeShortcutEntryPoints(): ReceiveChannel<List<ShortcutEntryPointInfo>>

    fun observeDashboardBottomShortcutEntryPoints(): ReceiveChannel<List<ShortcutEntryPointInfo>>

    fun observeDashboardOthersShortcutEntryPoints(): ReceiveChannel<List<ShortcutEntryPointInfo>>

    fun getActionEntryPoints(): List<ActionEntryPointInfo>

    fun getDemoApiEntryPointInfo(endpoint: String): Result<ApiEntryPointInfo>
}
