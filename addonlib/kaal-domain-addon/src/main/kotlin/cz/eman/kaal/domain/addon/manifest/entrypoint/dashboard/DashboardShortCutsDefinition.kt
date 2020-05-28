package cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard

import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo

/**
 * @author eMan s.r.o.
 */
data class DashboardShortCutsDefinition(
    val others: List<ShortcutEntryPointInfo> = emptyList(),
    var bottom: ShortcutEntryPointInfo? = null
)
