package cz.eman.kaal.presentation.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 * @see[AppCompatActivity]
 * @since 0.1.0
 */
abstract class BaseActivity : AppCompatActivity, CoroutineScope {

    override val coroutineContext = Dispatchers.Main + SupervisorJob()

    constructor(): super()

    constructor(@LayoutRes contentLayoutId: Int): super(contentLayoutId)

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}

//fun Activity.findParentNavController() = Navigation.findNavController(this, R.id.navHostFragment)
