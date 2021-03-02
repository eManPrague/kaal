package cz.eman.kaal.domain.action.manager

import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.result.Result

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
interface ActionProcessManager {

    /**
     * Will process action in all modules where action is supported and return list of results
     */
    suspend fun processAction(action: Action): Result<List<Result<ActionData>>>

    /**
     * Will process action and return first successful result (e.g. get Location data)
     */
    suspend fun processSingleAction(action: Action): Result<ActionData>
}
