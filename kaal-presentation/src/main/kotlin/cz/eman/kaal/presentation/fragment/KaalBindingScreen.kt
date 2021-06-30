@file:Suppress("unused")

package cz.eman.kaal.presentation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

/**
 * Interface which brings binding functionality to fragment or activity.
 *
 * @param B Type of data binding class corresponding [layoutId]
 * @author eMan a.s.
 * @see 0.9.0
 */
interface KaalBindingScreen<B : ViewDataBinding> {

    /**
     * The layout resource ID of the layout to inflate.
     */
    @get:LayoutRes
    val layoutId: Int

    /**
     * The binding for the inflated layout represented [layoutId]. Nullable to make sure it is cleared when screen is
     * destroyed (to prevent leaks).
     */
    @Suppress("PropertyName")
    var _binding: B?

    /**
     * The binding for the inflated layout represented [layoutId]
     */
    val binding: B
        get() = checkNotNull(_binding) {
            "Binding property is not accessible before onCreateView() or after onDestroyView()."
        }

    /**
     * Gets a lifecycle owner which is set to binding during creation.
     *
     * @return [LifecycleOwner]
     */
    fun getViewLifecycleOwner(): LifecycleOwner?

    /**
     * Function which creates (inflates) layout binding using [DataBindingUtil]. Sets lifecycle owner to it and
     * [_binding] property used to access binding.
     *
     * @param inflater used to inflate layout
     * @param container for layout (inflation)
     */
    fun onCreateViewInternal(inflater: LayoutInflater, container: ViewGroup?): View {
        return DataBindingUtil.inflate<B>(inflater, layoutId, container, false).apply {
            lifecycleOwner = getViewLifecycleOwner()
            _binding = this
        }.root
    }

    /**
     * Destroys the binding to make sure it does not leak. Should be called in onDestroy().
     */
    fun onDestroyViewInternal() {
        _binding = null
    }
}
