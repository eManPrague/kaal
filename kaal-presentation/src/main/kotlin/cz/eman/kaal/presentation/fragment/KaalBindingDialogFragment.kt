package cz.eman.kaal.presentation.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import cz.eman.logger.logInfo

/**
 * [DialogFragment] used for fragment that are shown as dialogs with specific view binding.
 *
 * @param B Type of data binding class corresponding [layoutId]
 * @param layoutId The layout resource ID of the layout to inflate.
 * @author eMan a.s.
 * @since 0.9.0
 */
@Suppress("unused")
abstract class KaalBindingDialogFragment<B : ViewDataBinding>(
    @LayoutRes override val layoutId: Int
) : DialogFragment(), KaalBindingScreen<B> {

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
        logInfo("${this.javaClass.simpleName}.onCreateView(arguments = $arguments, bundle = $savedInstanceState)")
        return onCreateViewInternal(inflater, container).apply {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    /**
     * Destroys view (and binding) by calling [onDestroyViewInternal].
     */
    override fun onDestroyView() {
        logInfo("${this.javaClass.simpleName}.onDestroyView()")
        onDestroyViewInternal()
        super.onDestroyView()
    }
}
