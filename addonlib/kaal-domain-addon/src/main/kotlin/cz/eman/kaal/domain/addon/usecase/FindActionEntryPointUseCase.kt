package cz.eman.kaal.domain.addon.usecase

import cz.eman.kaal.domain.addon.action.Action
import cz.eman.kaal.domain.addon.manifest.entrypoint.action.ActionEntryPointInfo
import cz.eman.kaal.domain.addon.usecase.FindActionEntryPointUseCase.Params
import cz.eman.kaal.domain.addon.app.KaalAddonApp
import cz.eman.kaal.domain.usecases.UseCase

/**
 *  Will return first entry point supporting given [ActionType] or null
 *
 *  @author eMan s.r.o.
 */
class FindActionEntryPointUseCase(private val addonApp: KaalAddonApp) :
    UseCase<ActionEntryPointInfo?, Params>() {

    override suspend fun doWork(params: Params): ActionEntryPointInfo? {
        return addonApp.getAddonInfo().getActionEntryPoints().find {
            it.actionType == params.action.actionType
        }
    }

    data class Params(val action: Action)
}
