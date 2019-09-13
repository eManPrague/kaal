package cz.eman.kaal.presentation.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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
 * Hide a keyboard from current view.
 * @since 0.3.0
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
