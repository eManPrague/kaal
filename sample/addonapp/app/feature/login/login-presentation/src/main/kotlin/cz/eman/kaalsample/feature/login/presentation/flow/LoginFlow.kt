package cz.eman.kaalsample.feature.login.presentation.flow

/**
 * @author eMan s.r.o.
 */
interface LoginFlow {

    fun getLoginFormDeepLink() = "kaalsample://loginFragment"

    fun onLoginFinishedAction()

    fun navigateToLogin()
}
