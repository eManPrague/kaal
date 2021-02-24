package cz.eman.kaal.sample.addon.action.module.resolver

import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.action.model.ActionName
import cz.eman.kaal.domain.action.resolver.ActionResolver
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.sample.addon.action.module.model.ModuleAction
import cz.eman.kaal.sample.addon.action.module.model.TestActionData

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
abstract class SimpleModuleActionResolver : ActionResolver {

    override suspend fun resolveAction(action: Action): Result<ActionData> {
        return when (action.name) {
            ModuleAction.TEST_ACTION -> {
                resolveTestAction(action as Action.DataAction)
            }
            else -> {
                TODO("NOT IMPLEMENTED")
            }
        }
    }

    private fun resolveTestAction(action: Action.DataAction): Result<ActionData> {
        // parse action data
        val data = TestActionData(data = action.data.jsonData!!)
        return onTestAction(data)
    }

    open fun onTestAction(data: TestActionData): Result<ActionData> {
        return Result.error(ErrorResult(message = "NOT IMPLEMENTED"))
    }

    override fun supportedActions(): Set<ActionName> {
        return setOf(
            ModuleAction.TEST_ACTION
        )
    }
}
