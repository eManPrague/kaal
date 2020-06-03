package cz.eman.kaal.sample.addon

import androidx.navigation.Navigation
import cz.eman.kaal.presentation.activity.KaalActivity
import cz.kaal.feature.app.core.navigation.NavFlow
import org.koin.android.ext.android.inject

class MainActivity : KaalActivity(R.layout.activity_main) {

    private val navFlow by inject<NavFlow>()

    override fun onResume() {
        super.onResume()
        navFlow.bind(Navigation.findNavController(this, R.id.rootNavHostFragment))
    }

    override fun onPause() {
        super.onPause()
        navFlow.unbind()
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(
            this,
            R.id.rootNavHostFragment
        ).navigateUp()
}
