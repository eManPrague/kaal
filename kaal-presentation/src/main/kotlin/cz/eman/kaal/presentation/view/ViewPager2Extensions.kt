@file:JvmName("ViewPagerExt")
@file:Suppress("unused")

package cz.eman.kaal.presentation.view

import androidx.annotation.Px
import androidx.viewpager2.widget.ViewPager2

/**
 * Add an action which will be invoked when the current page is scrolled, either as part
 * of a programmatically initiated smooth scroll or a user initiated touch scroll.
 *
 * @return The [ViewPager2.OnPageChangeCallback] added to the ViewPager
 * @see ViewPager2.OnPageChangeCallback.onPageScrolled
 * @since 0.9.0
 */
inline fun ViewPager2.doOnPageScrolled(
    crossinline action: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit
) = addOnPageChangeCallback(onPageScrolled = action)

/**
 * Add an action which will be invoked when a new page becomes selected. Animation is not
 * necessarily complete.
 *
 * @return The [ViewPager2.OnPageChangeCallback] added to the ViewPager
 * @see ViewPager2.OnPageChangeCallback.onPageSelected
 * @since 0.9.0
 */
inline fun ViewPager2.doOnPageSelected(crossinline action: (position: Int) -> Unit) =
    addOnPageChangeCallback(onPageSelected = action)

/**
 * Add an action which will be invoked when the scroll state changes.
 *
 * @return The [ViewPager2.OnPageChangeCallback] added to the ViewPager
 * @see ViewPager2.OnPageChangeCallback.onPageScrollStateChanged
 * @since 0.9.0
 */
inline fun ViewPager2.doOnPageScrollStateChanged(crossinline action: (state: Int) -> Unit) =
    addOnPageChangeCallback(onPageScrollStateChanged = action)

/**
 * Add a listener to this ViewPager using the provided actions.
 *
 * @return the [ViewPager2.OnPageChangeCallback] added to the ViewPager
 * @since 0.9.0
 */
inline fun ViewPager2.addOnPageChangeCallback(
    crossinline onPageScrolled: (
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) -> Unit = { _, _, _ -> },
    crossinline onPageSelected: (position: Int) -> Unit = {},
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {}
): ViewPager2.OnPageChangeCallback {
    val listener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            @Px positionOffsetPixels: Int
        ) = onPageScrolled(position, positionOffset, positionOffsetPixels)

        override fun onPageSelected(position: Int) = onPageSelected(position)
        override fun onPageScrollStateChanged(@ViewPager2.ScrollState state: Int) =
            onPageScrollStateChanged(state)
    }
    registerOnPageChangeCallback(listener)
    return listener
}
