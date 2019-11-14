package cz.eman.kaal.presentation.fragment

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[Fragment]
 * @since 0.1.0
 */
abstract class BaseFragment : Fragment(), CoroutineScope {

    override val coroutineContext = Dispatchers.Main + SupervisorJob()

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}

//fun Fragment.findParentNavController() = Navigation.findNavController(activity!!, R.id.navHostFragment)
