package cz.eman.kaal.infrastructure.action

import cz.eman.kaal.domain.action.error.ActionErrorCode
import cz.eman.kaal.domain.action.resolver.ActionResolver
import cz.eman.kaal.domain.action.error.ActionErrorResult
import cz.eman.kaal.domain.action.manager.ActionManager
import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.action.model.ActionName
import cz.eman.kaal.domain.result.Result

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
class ActionManagerImpl : ActionManager {

    private val resolvers: MutableMap<ActionName, MutableList<ActionResolver>> = mutableMapOf()

    override fun registerActionResolver(resolver: ActionResolver) {
        resolver.supportedActions().forEach {
            if (resolvers.containsKey(it)) {
                resolvers[it]!!.add(resolver)
            } else {
                resolvers[it] = mutableListOf(resolver)
            }
        }
    }

    override suspend fun processAction(action: Action): Result<List<Result<ActionData>>> {
        return if (resolvers.containsKey(action.name)) {
            val actionResults = resolvers[action.name]!!.map {
                val result = it.resolveAction(action)
                result
            }

            return actionResults.find { it.isSuccess() }?.let {
                Result.success(
                    actionResults
                )
            } ?: run {
                val errorText = "No action succeed for action [$action]"
                Result.error(
                    ActionErrorResult(message = errorText, code = ActionErrorCode.NO_ACTION_SUCCEED),
                    data = actionResults
                )
            }
        } else {
            val errorText = "No supporting module found for action [${action.name}]"
            Result.error(
                ActionErrorResult(
                    message = errorText,
                    code = ActionErrorCode.NO_SUPPORTING_MODULE_REGISTERED
                )
            )
        }
    }

    override suspend fun processSingleAction(action: Action): Result<ActionData> {
        return if (resolvers.containsKey(action.name)) {
            val resolver = resolvers[action.name]!!.first()
            return resolver.resolveAction(
                action = action
            )
        } else {
            val errorText = "No supporting module found for action [${action.name}]"
            Result.error(
                ActionErrorResult(
                    message = errorText,
                    code = ActionErrorCode.NO_SUPPORTING_MODULE_REGISTERED
                )
            )
        }
    }
}
