package cz.eman.kaal.presentation.addon.manifest.shortcut.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointInfo
import cz.eman.kaal.domain.addon.manifest.entrypoint.EntryPointParamKey

/**
 * @author eMan a.s.
 */
data class ShortcutVo(
    @StringRes val label: Int,
    @DrawableRes val icon: Int?,
    @StringRes val iconDescription: Int?,
    val orderWeight: Int,
    val entryPoint: EntryPointInfo,
    val navArgs: List<Pair<EntryPointParamKey, Any?>>?,
    var isLast: Boolean = false
)
