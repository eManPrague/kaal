package cz.eman.kaalsample.feature.splashscreen.presentation.flow

/**
 * @author eMan a.s.
 */
interface SplashScreenFlow {

    fun getSplashScreenDeepLink() = "kaalsample://splashScreenFragment"

    fun onSplashScreenFinishedAction()
}
