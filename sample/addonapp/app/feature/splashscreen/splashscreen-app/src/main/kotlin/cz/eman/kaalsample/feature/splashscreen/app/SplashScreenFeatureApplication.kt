package cz.eman.kaalsample.feature.splashscreen.app

import android.content.Context
import cz.eman.kaalsample.feature.splashscreen.app.di.splashScreenModule
import cz.kaal.feature.app.core.feature.KaalFeatureApplication
import org.koin.core.context.loadKoinModules

/**
 *
 * @author eMan s.r.o.
 * @see[KaalFeatureApplication]
 */
class SplashScreenFeatureApplication(override val appContext: Context) :
    KaalFeatureApplication(appContext) {

    override fun init() {
        loadKoinModules(splashScreenModule)
    }
}
