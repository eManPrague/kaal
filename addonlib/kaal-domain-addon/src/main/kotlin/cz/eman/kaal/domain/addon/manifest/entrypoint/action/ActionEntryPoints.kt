package cz.eman.kaal.domain.addon.manifest.entrypoint.action

import cz.eman.kaal.domain.addon.manifest.entrypoint.action.ActionEntryPointInfo

/**
 * @author eMan s.r.o.
 */
data class ActionEntryPoints(
    val addonId: String,
    val navGraphRef: Int,
    val actionType: List<ActionEntryPointInfo> = emptyList()
)
