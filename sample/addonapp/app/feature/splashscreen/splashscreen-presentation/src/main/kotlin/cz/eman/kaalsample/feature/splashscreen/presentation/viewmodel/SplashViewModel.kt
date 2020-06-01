package cz.eman.kaalsample.feature.splashscreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.eman.kaal.presentation.viewmodel.KaalViewModel
import cz.eman.kaalsample.feature.splashscreen.presentation.state.StartAppState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *  Splash view model
 *
 *  @author eMan s.r.o.
 *  @see[KaalViewModel]
 */
class SplashViewModel : KaalViewModel() {

    private val startAppStateMutable = MutableLiveData<StartAppState>()
    val startAppState: LiveData<StartAppState> = startAppStateMutable

    init {
        startApp()
    }

    private fun startApp() {
        viewModelScope.launch {
            delay(SPLASH_DELAY_TIME)
        }
    }

    fun goToLogin() {
        startAppStateMutable.value = StartAppState.SplashFinished
    }

    companion object {
        const val SPLASH_DELAY_TIME = 1000L
    }
}
