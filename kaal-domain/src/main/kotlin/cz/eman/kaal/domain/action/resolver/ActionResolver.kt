package cz.eman.kaal.domain.action.resolver

import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.action.model.ActionName
import cz.eman.kaal.domain.result.Result

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
interface ActionResolver {

    suspend fun resolveAction(action: Action): Result<ActionData>

    fun supportedActions(): Set<ActionName>
}
