package cz.eman.kaal.domain.addon.usecase

import cz.eman.kaal.domain.addon.action.Action
import cz.eman.kaal.domain.addon.app.KaalAddonApp
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult

/**
 *  Call addon or system functionality supporting given [cz.eman.kaal.domain.addon.action.model.ActionType]
 *
 *  @author eMan s.r.o.
 */
class CallActionUseCase(
    private val findActionEntryPoint: FindActionEntryPointUseCase,
    private val addonApp: KaalAddonApp
) : UseCaseResult<Boolean, CallActionUseCase.Params>() {

    override suspend fun doWork(params: Params): Result<Boolean> {
        val entryPoint = findActionEntryPoint(
            params = FindActionEntryPointUseCase.Params(params.action)
        )

        entryPoint?.let {
            addonApp.runAddon(
                entryPoint = it,
                data = params.action.params.toTypedArray()
            )
            return Result.Success(true)
        } ?: run {
            return Result.error(
                ErrorResult(
                    message = "No addon supporting given action=[${params.action}] found"
                )
            )
        }
    }

    data class Params(val action: Action)
}
