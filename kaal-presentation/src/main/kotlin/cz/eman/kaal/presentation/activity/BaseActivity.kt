package cz.eman.kaal.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.eman.kaal.presentation.di.ScopeAware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.getKoin
import kotlin.coroutines.CoroutineContext

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[AppCompatActivity]
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (this is ScopeAware) {
            // TODO KOIN Scopes need to be reviewed more deeply because of new version of KOIN
            // bind "custom" scope to component lifecycle
            bindScope(scope = getKoin().getOrCreateScope(scopeId), event = scopedLifecycleEvent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}



//fun Activity.findParentNavController() = Navigation.findNavController(this, R.id.navHostFragment)
