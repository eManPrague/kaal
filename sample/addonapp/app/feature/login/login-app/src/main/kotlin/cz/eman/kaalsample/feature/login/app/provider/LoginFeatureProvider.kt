package cz.eman.kaalsample.feature.login.app.provider

import cz.kaal.feature.app.core.feature.FeatureInitProvider
import cz.eman.kaalsample.feature.login.app.LoginFeatureApplication

/**
 * @author eMan a.s.
 * @see[FeatureInitProvider]
 */
class LoginFeatureProvider : FeatureInitProvider() {

    override fun provideFeatureApplication() = LoginFeatureApplication(context!!)
}
