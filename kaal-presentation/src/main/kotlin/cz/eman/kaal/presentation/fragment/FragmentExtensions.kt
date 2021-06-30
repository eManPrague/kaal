@file:Suppress("unused")

package cz.eman.kaal.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import cz.eman.kaal.presentation.fragment.property.FragmentPropertyDelegate

/**
 * @since 0.2.0
 */
fun <T : Any> argument() = FragmentPropertyDelegate<T>()

/**
 * An extension function to put arguments ([Bundle]) to your [Fragment].
 * @since 0.2.0
 */
inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}

/**
 * @since 0.2.0
 */
inline fun <reified T : View> Fragment.findOptional(id: Int): T? = view?.findViewById(id) as? T

/**
 * Shows a keyboard using activity from fragment.
 *
 * @since 0.9.0
 */
fun Fragment.showKeyboard() {
    (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}
