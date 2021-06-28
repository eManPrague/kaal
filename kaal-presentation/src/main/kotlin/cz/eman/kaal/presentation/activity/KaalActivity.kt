package cz.eman.kaal.presentation.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @see[AppCompatActivity]
 * @since 0.1.0
 */
abstract class KaalActivity @JvmOverloads constructor(
    @LayoutRes contentLayoutId: Int = 0
) : AppCompatActivity(contentLayoutId)

//fun Activity.findParentNavController() = Navigation.findNavController(this, R.id.navHostFragment)
