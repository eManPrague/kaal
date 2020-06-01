package cz.eman.kaalsample.feature.login.presentation.state

/**
 *  @author eMan a.s.
 */
sealed class LoginState {

    object IdleState : LoginState()
    object LoginDone : LoginState()
    object RegistrationDone : LoginState()

    class ErrorResult(val message: String?) : LoginState()
}
