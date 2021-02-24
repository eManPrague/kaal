package cz.eman.kaal.data.action.repository

import cz.eman.kaal.data.action.source.ActionDataSource
import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.action.repository.ActionRepository
import cz.eman.kaal.domain.result.Result

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
class ActionRepositoryImpl(
    private val actionDataSource: ActionDataSource
) : ActionRepository {

    override suspend fun processAction(action: Action): Result<List<Result<ActionData>>> {
        return actionDataSource.processAction(action)
    }

    override suspend fun processSingleAction(action: Action): Result<ActionData> {
        return actionDataSource.processSingleAction(action)
    }
}
