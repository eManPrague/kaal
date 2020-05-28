package cz.eman.kaal.domain.addon.manifest.entrypoint.demo

import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.DemoApiEntryPoint

/**
 * @author eMan s.r.o.
 */
data class DemoEntryPoints(
    val addonId: String,
    val navGraphRef: Int,
    val apiEntryPoints: DemoApiEntryPoint = DemoApiEntryPoint(
        addonId = addonId,
        navGraphRef = navGraphRef
    )
)
