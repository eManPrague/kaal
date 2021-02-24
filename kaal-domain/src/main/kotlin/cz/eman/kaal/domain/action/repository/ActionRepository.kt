package cz.eman.kaal.domain.action.repository

import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.result.Result

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
interface ActionRepository {

    suspend fun processAction(action: Action): Result<List<Result<ActionData>>>

    suspend fun processSingleAction(action: Action): Result<ActionData>
}
