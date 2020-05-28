package cz.eman.kaal.domain.addon.manifest.entrypoint.demo

/**
 * @author eMan s.r.o.
 */
interface ApiEntryPointInfo {

    val navGraphRef: Int
    val addonId: String
    val endpoint: String
    val responseFileName: String
}
