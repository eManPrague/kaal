package cz.eman.kaal.presentation.dialog

import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[DialogFragment]
 * @since 0.1.0
 */
abstract class BaseDialogFragment : DialogFragment(), CoroutineScope {

    override val coroutineContext = Dispatchers.Main + SupervisorJob()

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}
