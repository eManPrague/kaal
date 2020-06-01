package cz.eman.kaalsample.feature.login.app

import android.content.Context
import cz.kaal.feature.app.core.feature.KaalFeatureApplication
import cz.eman.kaalsample.feature.login.app.di.loginModule
import org.koin.core.context.loadKoinModules

/**
 *
 * @author eMan a.s.
 * @see[KaalFeatureApplication]
 */
class LoginFeatureApplication(override val appContext: Context) :
    KaalFeatureApplication(appContext) {

    override fun init() {
        loadKoinModules(loginModule)
    }
}
