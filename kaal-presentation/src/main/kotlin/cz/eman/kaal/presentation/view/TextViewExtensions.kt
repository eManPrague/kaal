package cz.eman.kaal.presentation.view

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * @since 0.3.0
 * @deprecated Deprecated since 0.9.0 - Use androidx.core.widget.addTextChangedListener()
 */
@Deprecated(
    "Deprecated since 0.9.0",
    ReplaceWith(
        "addTextChangedListener()",
        "androidx.core.widget"
    )
)
inline fun TextView.textWatcher(init: DslTextWatcher.() -> Unit) = addTextChangedListener(DslTextWatcher().apply(init))

/**
 * A DSL wrapper around the [TextWatcher]. Byt this use can avoid to using a boilerplate code when you need to be notified
 * if text has been changed or before change action and so on.
 *
 * @since 0.3.0
 * @see [TextWatcher]
 * @deprecated Deprecated since 0.9.0 - Use androidx.core.widget.addTextChangedListener()
 */
@Deprecated(
    "Deprecated since 0.9.0",
    ReplaceWith(
        "addTextChangedListener()",
        "androidx.core.widget"
    )
)
class DslTextWatcher : TextWatcher {

    val TextView.isEmpty
        get() = text.isEmpty()

    val TextView.isNotEmpty
        get() = text.isNotEmpty()

    val TextView.isBlank
        get() = text.isBlank()

    val TextView.isNotBlank
        get() = text.isNotBlank()

    private var beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var afterTextChanged: ((Editable?) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }

    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        beforeTextChanged = listener
    }

    fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        onTextChanged = listener
    }

    fun afterTextChanged(listener: (Editable?) -> Unit) {
        afterTextChanged = listener
    }
}
