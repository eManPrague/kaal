package cz.eman.kaal.domain.addon.usecase

import cz.eman.kaal.domain.addon.app.KaalAddonApp
import cz.eman.kaal.domain.addon.demo.repository.DemoModeRepository
import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.ApiEntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.usecases.UseCaseResult

/**
 *  Observe all registered [ShortcutEntryPointInfo]
 *
 *  @author eMan s.r.o.
 */
class FindDemoApiEntryPointUseCase(
    private val addonApp: KaalAddonApp,
    private val demoModeRepository: DemoModeRepository
) :
    UseCaseResult<ApiEntryPointInfo, FindDemoApiEntryPointUseCase.Params>() {

    data class Params(val endpoint: String)

    override suspend fun doWork(params: Params): Result<ApiEntryPointInfo> {
        val result = addonApp.getAddonInfo().getDemoApiEntryPointInfo(params.endpoint)
        return if (result.isSuccess()) {
            result
        } else {
            getCommonDemoApiEntryPoints(params.endpoint)
        }
    }

    private fun getCommonDemoApiEntryPoints(endpoint: String): Result<ApiEntryPointInfo> {
        return demoModeRepository.getDemoApiEntryPointInfo(endpoint)
    }
}
