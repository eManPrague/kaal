package cz.eman.kaal.sample.addon

import android.os.Bundle
import androidx.navigation.Navigation
import cz.eman.kaal.presentation.activity.KaalActivity
import cz.eman.kaal.sample.addon.action.module.model.TestActionData
import cz.eman.kaal.sample.addon.action.module.usecase.SendModuleTestActionUseCase
import cz.kaal.feature.app.core.navigation.NavFlow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : KaalActivity(R.layout.activity_main) {

    private val navFlow by inject<NavFlow>()
    private val sendTestActionUseCase by inject<SendModuleTestActionUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonSendSimpleAction.setOnClickListener {
            runBlocking {
                sendTestActionUseCase(params = SendModuleTestActionUseCase.Params(data = TestActionData("TEST ACTION DATA TO SHOW")))
            }
        }
    }

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
