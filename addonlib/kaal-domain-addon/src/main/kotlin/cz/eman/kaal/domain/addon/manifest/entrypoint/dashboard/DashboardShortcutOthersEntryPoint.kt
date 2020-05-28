package cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard

import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo

/**
 * @author eMan s.r.o.
 */
class DashboardShortcutOthersEntryPoint(
    val addonId: String,
    val navGraphRef: Int
) {
    val shortcuts = mutableListOf<ShortcutEntryPointInfo>()
}
