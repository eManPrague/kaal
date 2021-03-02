package cz.eman.kaal.sample.addon.app

import android.app.Application
import android.content.Context
import cz.eman.kaal.domain.action.manager.ActionManager
import cz.eman.kaal.domain.addon.AddonInfo
import cz.eman.kaal.domain.addon.app.AddonApplication
import cz.eman.kaal.domain.addon.app.KaalAddonApp
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointParamKey
import cz.eman.kaal.sample.addon.action.TestAddonActionResolver
import cz.eman.kaal.sample.addon.di.appModule
import cz.eman.kaal.sample.addon.manager.SampleAddonManagerImpl
import cz.kaal.feature.app.core.feature.FeatureAppRegistration
import cz.kaal.feature.app.core.feature.FeatureApplication
import cz.kaal.feature.app.core.navigation.NavFlow
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

/**
 *
 * @author eMan a.s.
 */
class SampleAddonApp : Application(), KaalAddonApp, FeatureAppRegistration, KoinComponent {

    private val navFlow: NavFlow by inject()
    private val addonManager: SampleAddonManagerImpl by inject()
    private val actionManager: ActionManager by inject()


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initKoin()
    }

    override fun onCreate() {
        super.onCreate()
        initAddons()
        registerTestAddonActionResolver()
    }

    private fun registerTestAddonActionResolver() {
        actionManager.registerActionResolver(resolver = TestAddonActionResolver(this))
    }

    override fun registerAddonApplication(addonApplication: AddonApplication): Boolean {

        val result = addonManager.registerAddonApplication(addonApplication)
        if (result) {
            addonApplication.getActionResolver()?.let {
                actionManager.registerActionResolver(it)
            }
        }
        return result
    }

    override fun getAddonInfo(): AddonInfo = addonManager

    override fun runAddon(
        entryPoint: EntryPointInfo,
        data: Array<out Pair<EntryPointParamKey, Any?>>
    ) {
        navFlow.runAddon(entryPoint, data)
    }

    private fun initAddons() {
        addonManager.initAddons()
    }

    override fun initFeature(application: FeatureApplication): Boolean {
        application.init()
        return true
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@SampleAddonApp)
            modules(
                listOf(
                    appModule
                )
            )
        }
    }
}
