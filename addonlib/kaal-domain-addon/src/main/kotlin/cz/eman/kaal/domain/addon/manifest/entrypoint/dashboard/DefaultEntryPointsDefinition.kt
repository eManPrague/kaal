package cz.eman.kaal.domain.addon.manifest.entrypoint.dashboard

import cz.eman.kaal.domain.addon.manifest.entrypoint.action.ActionEntryPointDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointsDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.DemoEntryPointsDefinition
import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointDefinition

/**
 * @author eMan s.r.o.
 */
open class DefaultEntryPointsDefinition(
    val dashboardDefinition: DashboardEntryPointsDefinition,
    val shortcutDefinition: ShortcutEntryPointDefinition?,
    val actionDefinition: ActionEntryPointDefinition?,
    val demoDefinition: DemoEntryPointsDefinition?
) :
    EntryPointsDefinition {

    override fun dashboardDefinition() = dashboardDefinition

    override fun shortcutDefinition(): ShortcutEntryPointDefinition? = shortcutDefinition

    override fun actionDefinition(): ActionEntryPointDefinition? = actionDefinition

    override fun demoDefinition(): DemoEntryPointsDefinition? = demoDefinition
}
