package cz.eman.kaal.sample.addon.di

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
}
