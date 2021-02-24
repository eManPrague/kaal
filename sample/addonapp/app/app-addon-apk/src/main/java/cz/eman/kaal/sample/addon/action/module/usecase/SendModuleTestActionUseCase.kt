package cz.eman.kaal.sample.addon.action.module.usecase

import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.action.usecase.ProcessSingleActionUseCase
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaal.sample.addon.action.module.model.ModuleAction
import cz.eman.kaal.sample.addon.action.module.model.TestActionData

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
class SendModuleTestActionUseCase(
    private val processSingleAction: ProcessSingleActionUseCase
) : UseCaseResult<Unit, SendModuleTestActionUseCase.Params>() {

    override suspend fun doWork(params: Params): Result<Unit> {
        val result = processSingleAction(params = ProcessSingleActionUseCase.Params(
            action = parseActionData(params.data)
        ))
        return when (result) {
            is Result.Success -> {
               Result.success(Unit)
            }
            is Result.Error -> {
                Result.error(result.error)
            }
        }
    }

    private fun parseActionData(data: TestActionData): Action {
        return Action.DataAction(name = ModuleAction.TEST_ACTION, data = ActionData(data.data))
    }

    data class Params(
        val data: TestActionData
    )
}
