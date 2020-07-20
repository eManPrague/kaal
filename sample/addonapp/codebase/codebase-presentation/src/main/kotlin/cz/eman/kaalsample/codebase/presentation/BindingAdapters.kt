package cz.eman.kaalsample.codebase.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter

/**
 * Generic binding adapters for views.
 *
 * @author eMan a.s.
 */
@BindingAdapter("android:layout_marginTop")
fun setMarginTop(view: View, margin: Float) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> { topMargin = margin.toInt() }
}

@BindingAdapter("android:layout_marginBottom")
fun setMarginBottom(view: View, margin: Float) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> { bottomMargin = margin.toInt() }
}

@BindingAdapter("android:layout_marginLeft")
fun setMarginLeft(view: View, margin: Float) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> { leftMargin = margin.toInt() }
}

@BindingAdapter("android:layout_marginStart")
fun setMarginStart(view: View, margin: Float) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> { marginStart = margin.toInt() }
}

@BindingAdapter("android:layout_marginRight")
fun setMarginRight(view: View, margin: Float) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> { rightMargin = margin.toInt() }
}

@BindingAdapter("android:layout_marginEnd")
fun setMarginEnd(view: View, margin: Float) {
    view.updateLayoutParams<ViewGroup.MarginLayoutParams> { marginEnd = margin.toInt() }
}

@BindingAdapter("android:src")
fun setImageResource(view: ImageView, @DrawableRes drawableId: Int) {
    view.setImageResource(drawableId)
}
