package cz.eman.kaal.presentation.fragment

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * TODO CLASS_DESCRIPTION
 *
 * @author eMan a.s.
 */
sealed class NavigationIcon(
    @DrawableRes
    open val navIcon: Int,
    @StringRes
    open val navIconDescription: Int
) {
    object Default : NavigationIcon(
        navIcon = com.google.android.material.R.drawable.abc_ic_ab_back_material,
        navIconDescription = com.google.android.material.R.string.abc_action_bar_up_description
    )

    data class CustomIcon(
        override val navIcon: Int,
        override val navIconDescription: Int
    ) : NavigationIcon(
        navIcon = navIcon,
        navIconDescription = navIconDescription
    )
}
