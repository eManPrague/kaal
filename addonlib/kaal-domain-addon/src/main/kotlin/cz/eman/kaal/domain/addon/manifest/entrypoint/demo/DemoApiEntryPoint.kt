package cz.eman.kaal.domain.addon.manifest.entrypoint.demo

import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.ApiEntryPointInfo

/**
 * @author eMan s.r.o.
 */
class DemoApiEntryPoint(
    val addonId: String,
    val navGraphRef: Int
) {
    var apiEntryPointsInfo = listOf<ApiEntryPointInfo>()
}
