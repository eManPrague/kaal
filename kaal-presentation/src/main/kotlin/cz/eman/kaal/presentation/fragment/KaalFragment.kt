package cz.eman.kaal.presentation.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @see[Fragment]
 * @since 0.1.0
 */
abstract class KaalFragment @JvmOverloads constructor(
    @LayoutRes contentLayoutId: Int = 0
) : Fragment(contentLayoutId)

//fun Fragment.findParentNavController() = Navigation.findNavController(activity!!, R.id.navHostFragment)
