package cz.csob.smartbanking.feature.login.presentation.navigation

/**
 * @author eMan s.r.o.
 */
interface LoginFlow {

    fun getLoginFormDeepLink() = "smartbanking://loginFragment"

    fun onLoginFinishedAction()

    fun navigateToLogin()
}
