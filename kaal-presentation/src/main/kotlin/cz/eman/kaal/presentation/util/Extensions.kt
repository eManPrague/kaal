package cz.eman.kaal.presentation.util

import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Additional extensions.
 *
 * @author eMan a.s.
 */
fun Context.getIms(): InputMethodManager? = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
