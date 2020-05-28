package cz.eman.kaal.domain.addon.manifest.entrypoint.action

import cz.eman.kaal.domain.addon.action.model.ActionType
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointInfo

/**
 * @author eMan s.r.o.
 */
interface ActionEntryPointInfo : EntryPointInfo {
    val actionType: ActionType
}
