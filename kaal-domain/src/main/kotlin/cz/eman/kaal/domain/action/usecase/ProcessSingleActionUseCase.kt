package cz.eman.kaal.domain.action.usecase

import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.repository.ActionRepository
import cz.eman.kaal.domain.action.usecase.ProcessSingleActionUseCase.Params
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
class ProcessSingleActionUseCase(
    private val actionRepository: ActionRepository
) : UseCaseResult<ActionData, Params>() {

    override suspend fun doWork(params: Params): Result<ActionData> {
        return actionRepository.processSingleAction(params.action)
    }

    data class Params(
        val action: Action
    )
}
