package cz.eman.kaal.sample.addon.navigation

import cz.csob.smartbanking.feature.login.presentation.navigation.LoginFlow
import cz.eman.kaalsample.feature.splashscreen.presentation.flow.SplashScreenFlow
import cz.kaal.feature.app.core.navigation.NavFlow

/**
 * By this class a flows of all features available in the app is handle by using [NavController].
 *
 * Each feature must define own flow interface which must be implemented by this class.
 * Based on these own flow interfaces a flow of main app is handled.
 *
 * @author eMan a.s.
 * @see[NavFlow]
 * @see[SplashScreenFlow]
 * @see[LoginFlow]
 */
class NavFlowImpl : NavFlow(), SplashScreenFlow, LoginFlow {

    override fun onSplashScreenFinishedAction() {
        navigateToLogin()
    }

    override fun onLoginFinishedAction() {
        TODO("Not yet implemented")
    }

    override fun navigateToLogin() {
        navigateTo(getLoginFormDeepLink())
    }
}
