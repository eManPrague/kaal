package cz.eman.kaal.sample.addon.action

import android.content.Context
import android.widget.Toast
import cz.eman.kaal.domain.action.model.ActionData
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.sample.addon.action.module.model.TestActionData
import cz.eman.kaal.sample.addon.action.module.resolver.SimpleModuleActionResolver

/**
 *
 *  @author Roman Holomek <roman.holomek@gmail.com>
 */
class TestAddonActionResolver(
    private val context: Context
) : SimpleModuleActionResolver() {

    override fun onTestAction(data: TestActionData): Result<ActionData> {
        showMessage("TEST ACTION RECEIVED [data: ${data.data}]")
        return Result.success(ActionData())
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
