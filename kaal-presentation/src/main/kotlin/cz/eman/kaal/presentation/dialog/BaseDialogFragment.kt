package cz.eman.kaal.presentation.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import cz.eman.kaal.presentation.di.ScopeAware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.getKoin

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[DialogFragment]
 */
abstract class BaseDialogFragment : DialogFragment(), CoroutineScope {

    override val coroutineContext = Dispatchers.Main + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (this is ScopeAware) {
            // bind "custom" scope to component lifecycle
            bindScope(scope = getKoin().getOrCreateScope(scopeId), event = scopedLifecycleEvent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}