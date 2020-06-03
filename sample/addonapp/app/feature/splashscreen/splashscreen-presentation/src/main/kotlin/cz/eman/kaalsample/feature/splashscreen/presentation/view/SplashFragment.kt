package cz.eman.kaalsample.feature.splashscreen.presentation.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import cz.eman.kaal.presentation.fragment.KaalFragment
import cz.eman.kaalsample.feature.splashscreen.presentation.R
import cz.eman.kaalsample.feature.splashscreen.presentation.flow.SplashScreenFlow
import cz.eman.kaalsample.feature.splashscreen.presentation.state.StartAppState
import cz.eman.kaalsample.feature.splashscreen.presentation.viewmodel.SplashViewModel
import cz.kaal.feature.app.core.navigation.NavFlow
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author eMan s.r.o.
 */
class SplashFragment : KaalFragment(R.layout.fragment_splash) {

    private val viewModel by viewModel<SplashViewModel>()
    private val flow by inject<NavFlow>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()
    }

    private fun registerEvents() {
        viewModel.startAppState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is StartAppState.SplashFinished ->
                    (flow as SplashScreenFlow).onSplashScreenFinishedAction()
            }
        })
    }
}
