package cz.eman.kaal.domain.addon.action

import cz.eman.kaal.domain.addon.action.model.ActionType
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointParamKey

sealed class Action(
    val actionType: ActionType,
    val params: MutableList<Pair<EntryPointParamKey, Any>> = mutableListOf()
) {

    data class WebView(val url: String) : Action(ActionType.WebView) {
        init {
            params.add(WebViewEntryPointParamKey.URL to url)
        }

        enum class WebViewEntryPointParamKey(override var keyName: String) : EntryPointParamKey {
            URL("url")
        }
    }
}
