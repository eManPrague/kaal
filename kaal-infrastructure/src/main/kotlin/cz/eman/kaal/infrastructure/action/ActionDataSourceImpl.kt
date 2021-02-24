package cz.eman.kaal.infrastructure.action

import cz.eman.kaal.data.action.source.ActionDataSource
import cz.eman.kaal.domain.action.manager.ActionManager
import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.result.Result

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
class ActionDataSourceImpl(
    private val actionProcessManager: ActionManager
) : ActionDataSource {

    override suspend fun processAction(action: Action): Result<List<Result<ActionData>>> {
        return actionProcessManager.processAction(action)
    }

    override suspend fun processSingleAction(action: Action): Result<ActionData> {
        return actionProcessManager.processSingleAction(action)
    }
}
