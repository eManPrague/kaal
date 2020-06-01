package cz.eman.kaalsample.feature.login.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaal.feature.login.domain.usecase.AuthorizeUserUseCase
import cz.eman.kaal.feature.login.domain.usecase.RegisterUserUseCase
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaalsample.feature.login.presentation.model.LoginMode
import cz.eman.kaalsample.feature.login.presentation.state.LoginState
import cz.eman.logger.logDebug
import cz.eman.logger.logVerbose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * @author eMan a.s.
 */
class LoginViewModel(
    private val authoriseUser: AuthorizeUserUseCase,
    private val registerUser: RegisterUserUseCase
) : KaalViewModel() {

    private val loginEventMutable = MutableLiveData<LoginState>()
    val loginEvent: LiveData<LoginState> = loginEventMutable

    var loginMode = LoginMode.AUTHORIZATION

    fun processUser(userName: String, password: String) {
        val user = User(userName, password)
        logVerbose("process user: $userName with use-case login = $loginMode")
        if (loginMode == LoginMode.AUTHORIZATION) {
            loginUser(user)
        } else {
            registerUser(user)
        }
    }

    private fun loginUser(user: User) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { authoriseUser(AuthorizeUserUseCase.Params(user)) }
            logDebug(" authorisation result: $result")
            when (result) {
                is Result.Success -> loginEventMutable.value = LoginState.LoginDone
                is Result.Error -> loginEventMutable.value = LoginState.ErrorResult(result.error.message)
            }
        }
    }

    private fun registerUser(user: User) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { registerUser(RegisterUserUseCase.Params(user)) }
            logDebug(" registration result: $result")
            when (result) {
                is Result.Success -> loginEventMutable.value = LoginState.RegistrationDone
                is Result.Error -> loginEventMutable.value = LoginState.ErrorResult(result.error.message)
            }
        }
    }
}
