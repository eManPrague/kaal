package cz.eman.kaal.domain.addon.manifest.entrypoint.action

/**
 * @author eMan s.r.o.
 */
class ActionsEntryPoint(
    val addonId: String,
    val navGraphRef: Int
) {
    val actions = mutableListOf<ActionEntryPointInfo>()
}
