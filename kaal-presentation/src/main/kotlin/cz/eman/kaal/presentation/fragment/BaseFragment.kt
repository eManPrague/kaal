package cz.eman.kaal.presentation.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[Fragment]
 * @since 0.1.0
 */
abstract class BaseFragment @JvmOverloads constructor(
    @LayoutRes contentLayoutId: Int = 0
) : Fragment(contentLayoutId)

//fun Fragment.findParentNavController() = Navigation.findNavController(activity!!, R.id.navHostFragment)
