package cz.eman.kaalsample.feature.login.app.di

import cz.eman.kaal.feature.login.domain.repository.UserAuthRepository
import cz.eman.kaal.feature.login.domain.usecase.AuthorizeUserUseCase
import cz.eman.kaal.feature.login.domain.usecase.RegisterUserUseCase
import cz.eman.kaalsample.feature.login.infrastructure.db.UserDatabase
import cz.eman.kaalsample.feature.login.infrastructure.source.UserAuthLocalDataSource
import cz.eman.kaalsample.feature.login.presentation.viewmodel.LoginViewModel
import cz.kaal.sample.feature.login.data.repository.UserAuthRepositoryImpl
import cz.kaal.sample.feature.login.data.source.UserAuthDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author: eMan a.s.
 */
val loginModule = module {

    viewModel {
        LoginViewModel(
            authoriseUser = get(),
            registerUser = get(),
            flow = get()
        )
    }

    single { UserDatabase.getInstance(context = get(), factory = null) }

    single<UserAuthDataSource> { UserAuthLocalDataSource(userDao = get<UserDatabase>().userDao) }

    single<UserAuthRepository> { UserAuthRepositoryImpl(userDataSource = get()) }

    factory { AuthorizeUserUseCase(userRepository = get()) }

    factory { RegisterUserUseCase(userRepository = get()) }
}
