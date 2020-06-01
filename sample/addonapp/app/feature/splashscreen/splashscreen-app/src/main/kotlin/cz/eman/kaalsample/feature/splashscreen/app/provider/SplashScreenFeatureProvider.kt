package cz.eman.kaalsample.feature.splashscreen.app.provider

import cz.eman.kaalsample.feature.splashscreen.app.SplashScreenFeatureApplication
import cz.kaal.feature.app.core.feature.FeatureInitProvider

/**
 * @author eMan a.s.
 * @see[FeatureInitProvider]
 */
class SplashScreenFeatureProvider : FeatureInitProvider() {

    override fun provideFeatureApplication() =
        SplashScreenFeatureApplication(requireNotNull(context))
}
