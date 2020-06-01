package cz.eman.kaalsample.feature.login.app.di

import cz.eman.kaalsample.feature.login.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author: eMan a.s.
 */
val loginModule = module {

    viewModel {
        LoginViewModel(
            authoriseUser = get(),
            registerUser = get()
        )
    }
}
