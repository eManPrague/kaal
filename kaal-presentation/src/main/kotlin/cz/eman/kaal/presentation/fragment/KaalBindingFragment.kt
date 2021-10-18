package cz.eman.kaal.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import cz.eman.logger.logInfo

/**
 * Binding fragment which has binding functionality implemented using [KaalBindingScreen].
 *
 * @param B Type of data binding class corresponding [layoutId]
 * @param layoutId The layout resource ID of the layout to inflate.
 * @author eMan a.s.
 * @since 0.9.0
 */
@Suppress("unused")
abstract class KaalBindingFragment<B : ViewDataBinding>(
    @LayoutRes override val layoutId: Int
) : KaalFragment(), KaalBindingScreen<B> {

    /**
     * The binding for the inflated layout represented [layoutId]. Nullable to make sure it is cleared when screen is
     * destroyed (to prevent leaks).
     */
    final override var _binding: B? = null

    /**
     * Creates view (and binding) by calling [onCreateViewInternal].
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logInfo("onCreateView(arguments = $arguments, bundle = $savedInstanceState)")
        return onCreateViewInternal(inflater, container)
    }

    /**
     * Destroys view (and binding) by calling [onDestroyViewInternal].
     */
    override fun onDestroyView() {
        logInfo("onDestroyView()")
        onDestroyViewInternal()
        super.onDestroyView()
    }
}
