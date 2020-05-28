package cz.eman.kaal.domain.addon.manifest.entrypoint

import cz.eman.kaal.domain.addon.manifest.entrypoint.action.ActionEntryPointDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard.DashboardEntryPointsDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.DemoEntryPointsDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointDefinition

/**
 * @author eMan s.r.o.
 */
interface EntryPointsDefinition {

    fun dashboardDefinition(): DashboardEntryPointsDefinition

    fun shortcutDefinition(): ShortcutEntryPointDefinition?

    fun actionDefinition(): ActionEntryPointDefinition?

    fun demoDefinition(): DemoEntryPointsDefinition?
}
