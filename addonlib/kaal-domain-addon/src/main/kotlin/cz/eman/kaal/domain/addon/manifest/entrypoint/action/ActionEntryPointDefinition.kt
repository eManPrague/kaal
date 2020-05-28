package cz.eman.kaal.domain.addon.manifest.entrypoint.action

/**
 * @author eMan s.r.o.
 */
data class ActionEntryPointDefinition(
    val actions: List<ActionEntryPointInfo> = emptyList()
)
