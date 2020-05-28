package cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard

/**
 * @author eMan s.r.o.
 */
data class DashboardEntryPoints(
    val addonId: String,
    val navGraphRef: Int,
    val shortcutEntryPoints: DashboardShortcutsEntryPoint = DashboardShortcutsEntryPoint(
        addonId = addonId,
        navGraphRef = navGraphRef
    )
)
