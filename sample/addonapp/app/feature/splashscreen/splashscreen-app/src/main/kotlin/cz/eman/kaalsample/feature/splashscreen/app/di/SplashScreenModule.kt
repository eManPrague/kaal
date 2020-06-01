package cz.eman.kaalsample.feature.splashscreen.app.di

import cz.eman.kaalsample.feature.splashscreen.presentation.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashScreenModule = module {

    viewModel {
        SplashViewModel()
    }
}
