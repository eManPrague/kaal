package cz.eman.kaal.domain.addon.usecase

import cz.eman.kaal.domain.addon.app.KaalAddonApp
import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo
import cz.eman.kaal.domain.usecases.UseCaseNoParams
import kotlinx.coroutines.channels.ReceiveChannel

/**
 *  Observe all registered [ShortcutEntryPointInfo]
 *
 *  @author eMan s.r.o.
 */
class ObserveAllShortcutEntryPointsUseCase(private val addonApp: KaalAddonApp) :
    UseCaseNoParams<ReceiveChannel<List<ShortcutEntryPointInfo>>>() {

    override suspend fun doWork(params: Unit): ReceiveChannel<List<ShortcutEntryPointInfo>> {
        return addonApp.getAddonInfo().observeShortcutEntryPoints()
    }
}
