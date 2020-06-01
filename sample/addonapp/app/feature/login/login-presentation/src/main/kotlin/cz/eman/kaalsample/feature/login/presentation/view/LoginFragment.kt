package cz.eman.kaalsample.feature.login.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.print.PrintHelper
import cz.eman.kaal.presentation.fragment.KaalFragment
import cz.eman.kaalsample.feature.login.presentation.R
import cz.eman.kaalsample.feature.login.presentation.model.LoginMode
import cz.eman.kaalsample.feature.login.presentation.state.LoginState
import cz.eman.kaalsample.feature.login.presentation.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 *
 * @author eMan a.s.
 * @see[KaalFragment]
 */
class LoginFragment : KaalFragment(R.layout.fragment_login) {

    private val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (requireActivity().let { resources.configuration.orientation }) {
            PrintHelper.ORIENTATION_PORTRAIT -> view.setBackgroundResource(R.drawable.splash_land)
            PrintHelper.ORIENTATION_LANDSCAPE -> view.setBackgroundResource(R.drawable.splash)
        }
        initLayout()
        registerEvents()
    }

    private fun initLayout() {
        switchMode(viewModel.loginMode == LoginMode.AUTHORIZATION)

        switchLogin.setOnClickListener {
            switchMode(viewModel.loginMode == LoginMode.REGISTRATION)
        }

        loginButton.setOnClickListener {
            viewModel.processUser(
                userName = loginUserName.text.toString().trim(),
                password = loginPassword.text.toString().trim()
            )
        }

        eman.setOnClickListener {
            loginUserName.setText("john")
            loginPassword.setText("travolta")
        }

    }

    private fun switchMode(isAuthorization: Boolean) {
        viewModel.loginMode = (if (isAuthorization) LoginMode.AUTHORIZATION else LoginMode.REGISTRATION)
        if (isAuthorization) {
            switchLogin.setText(R.string.login_screen_register_u)
            loginButton.setText(R.string.login_screen_login)
        } else {
            switchLogin.setText(R.string.login_screen_login_u)
            loginButton.setText(R.string.login_screen_register)
        }
    }

    private fun registerEvents() {
        viewModel.loginEvent.observe(viewLifecycleOwner, Observer { updateViewState(it) })
    }

    private fun updateViewState(state: LoginState) {
        when (state) {
            is LoginState.IdleState -> Timber.v("waiting to user")
            is LoginState.LoginDone, LoginState.RegistrationDone -> userAuthorized()
            is LoginState.ErrorResult -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun userAuthorized() {
        // TODO implement it!!!
    }
}
