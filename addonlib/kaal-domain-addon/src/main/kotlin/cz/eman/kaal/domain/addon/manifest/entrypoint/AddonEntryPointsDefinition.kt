package cz.eman.kaal.domain.addon.manifest.entrypoint

import cz.eman.kaal.domain.addon.manifest.entrypoint.action.ActionEntryPointDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard.DashboardEntryPointsDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard.DefaultEntryPointsDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.DemoEntryPointsDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointDefinition

/**
 *
 * @author eMan s.r.o.
 */
class AddonEntryPointsDefinition(
    val dashboard: DashboardEntryPointsDefinition,
    val shortcut: ShortcutEntryPointDefinition?,
    val actions: ActionEntryPointDefinition?,
    val demo: DemoEntryPointsDefinition
) :
    DefaultEntryPointsDefinition(
        dashboardDefinition = dashboard,
        shortcutDefinition = shortcut,
        actionDefinition = actions,
        demoDefinition = demo
    )
