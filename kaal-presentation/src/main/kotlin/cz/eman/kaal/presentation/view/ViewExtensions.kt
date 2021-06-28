@file:Suppress("unused")

package cz.eman.kaal.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes

/**
 * By calling this function you can make your view component visible ([View.VISIBLE])
 * @since 0.3.0
 */
fun View.show() {
    if (View.VISIBLE != this.visibility) {
        this.visibility = View.VISIBLE
    }
}

/**
 * By calling this function you can make your view component [View.GONE].
 * @since 0.3.0
 */
fun View.hide() {
    if (View.GONE != this.visibility) {
        this.visibility = View.GONE
    }
}

/**
 * By calling this function you can make your view component invisible ([View.INVISIBLE])
 * @since 0.3.0
 */
fun View.invisible() {
    if (View.INVISIBLE != this.visibility) {
        this.visibility = View.INVISIBLE
    }
}

/**
 * Shows a keyboard using context of the view.
 *
 * @since 0.9.0
 */
fun View.showKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}

/**
 * Hide a keyboard from current view.
 *
 * @since 0.3.0
 */
fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(windowToken, 0)
    }
}

/**
 * Allows calls like:
 *
 *  ```
 *      viewGroup.inflate(R.layout.exchange_rates_view)
 *  ```
 *  @since 0.6.0
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}
