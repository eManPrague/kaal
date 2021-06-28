@file:JvmName("TabLayoutExt")
@file:Suppress("unused")

package cz.eman.kaal.presentation.view

import com.google.android.material.tabs.TabLayout

/**
 * Add an action which will be invoked when a tab enters the selected state.
 *
 * @return The [TabLayout.OnTabSelectedListener] added to the TabLayout
 * @see TabLayout.OnTabSelectedListener.onTabSelected
 * @since 0.9.0
 */
inline fun TabLayout.doOnTabSelected(crossinline action: (tab: TabLayout.Tab) -> Unit) =
    addOnTabSelectedListener(onTabSelected = action)

/**
 * Add an action which will be invoked when a tab exits the selected state.
 *
 * @return The [TabLayout.OnTabSelectedListener] added to the TabLayout
 * @see TabLayout.OnTabSelectedListener.onTabUnselected
 * @since 0.9.0
 */
inline fun TabLayout.doOnTabUnselected(crossinline action: (tab: TabLayout.Tab) -> Unit) =
    addOnTabSelectedListener(onTabUnselected = action)

/**
 * Add an action which will be invoked when a tab that is already selected
 * is chosen again by the user.
 *
 * @return The [TabLayout.OnTabSelectedListener] added to the TabLayout
 * @see TabLayout.OnTabSelectedListener.onTabReselected
 * @since 0.9.0
 */
inline fun TabLayout.doOnTabReselected(crossinline action: (tab: TabLayout.Tab) -> Unit) =
    addOnTabSelectedListener(onTabReselected = action)

/**
 * Add a listener to this ViewPager using the provided actions.
 *
 * @return The [TabLayout.OnTabSelectedListener] added to the TabLayout
 * @since 0.9.0
 */
inline fun TabLayout.addOnTabSelectedListener(
    crossinline onTabSelected: (tab: TabLayout.Tab) -> Unit = {},
    crossinline onTabUnselected: (tab: TabLayout.Tab) -> Unit = {},
    crossinline onTabReselected: (tab: TabLayout.Tab) -> Unit = {}
): TabLayout.OnTabSelectedListener {
    val listener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) = onTabSelected(tab)
        override fun onTabUnselected(tab: TabLayout.Tab) = onTabUnselected(tab)
        override fun onTabReselected(tab: TabLayout.Tab) = onTabReselected(tab)
    }
    addOnTabSelectedListener(listener)
    return listener
}
