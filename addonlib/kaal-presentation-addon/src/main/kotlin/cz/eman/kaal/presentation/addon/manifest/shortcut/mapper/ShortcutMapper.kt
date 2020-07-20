package cz.eman.kaal.presentation.addon.manifest.shortcut.mapper

import cz.eman.kaal.domain.addon.manifest.entrypoint.shortcut.ShortcutEntryPointInfo
import cz.eman.kaal.presentation.addon.manifest.shortcut.model.ShortcutVo

/**
 * @author eMan s.r.o.
 */
object ShortcutMapper {

    fun mapToViewObject(entryPoint: ShortcutEntryPointInfo) =
        ShortcutVo(
            label = entryPoint.label,
            icon = entryPoint.icon,
            iconDescription = entryPoint.iconDescription ?: entryPoint.label,
            orderWeight = entryPoint.orderWeight,
            entryPoint = entryPoint,
            navArgs = entryPoint.navArgs
        )
}
