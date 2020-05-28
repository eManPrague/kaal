package cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard

import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo

/**
 * @author eMan s.r.o.
 */
class DashboardShortcutsEntryPoint(
    val addonId: String,
    val navGraphRef: Int,
    val othersShortcutEntryPoint: DashboardShortcutOthersEntryPoint =
        DashboardShortcutOthersEntryPoint(
            addonId,
            navGraphRef
        )
) {
    var bottom: ShortcutEntryPointInfo? = null
}
