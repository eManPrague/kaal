package cz.eman.kaal.sample.addon.di

import cz.eman.kaal.infrastructure.action.ActionManagerImpl
import cz.eman.kaal.data.action.repository.ActionRepositoryImpl
import cz.eman.kaal.data.action.source.ActionDataSource
import cz.eman.kaal.domain.action.manager.ActionManager
import cz.eman.kaal.domain.action.repository.ActionRepository
import cz.eman.kaal.domain.action.usecase.ProcessSingleActionUseCase
import cz.eman.kaal.infrastructure.action.ActionDataSourceImpl
import cz.eman.kaal.sample.addon.action.module.usecase.SendModuleTestActionUseCase
import cz.eman.kaal.sample.addon.manager.SampleAddonManagerImpl
import cz.eman.kaal.sample.addon.navigation.NavFlowImpl
import cz.kaal.feature.app.core.navigation.NavFlow
import org.koin.dsl.module

/**
 *
 * @author eMan a.s.
 */
val appModule = module {

    single<NavFlow> { NavFlowImpl() }

    single { SampleAddonManagerImpl() }

    single<ActionManager> { ActionManagerImpl() }

    factory<ActionRepository> { ActionRepositoryImpl(actionDataSource = get()) }

    single<ActionDataSource> { ActionDataSourceImpl(actionProcessManager = get()) }

    factory { ProcessSingleActionUseCase(actionRepository = get()) }
    factory { SendModuleTestActionUseCase(processSingleAction = get()) }
}
