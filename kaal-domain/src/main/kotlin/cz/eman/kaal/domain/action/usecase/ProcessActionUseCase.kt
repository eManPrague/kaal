package cz.eman.kaal.domain.action.usecase

import cz.eman.kaal.domain.action.repository.ActionRepository
import cz.eman.kaal.domain.action.model.Action
import cz.eman.kaal.domain.action.usecase.ProcessActionUseCase.Params
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaal.domain.result.Result

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
class ProcessActionUseCase(
    private val actionRepository: ActionRepository
) : UseCaseResult<List<Result<ActionData>>, Params>() {

    override suspend fun doWork(params: Params): Result<List<Result<ActionData>>> {
        return actionRepository.processAction(params.action)
    }

    data class Params(
        val action: Action
    )
}
