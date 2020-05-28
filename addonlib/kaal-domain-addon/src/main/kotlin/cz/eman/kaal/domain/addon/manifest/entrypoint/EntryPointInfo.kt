package cz.eman.kaal.domain.addon.manifest.entrypoint

/**
 * @author eMan s.r.o.
 */
interface EntryPointInfo {
    val entryPointId: EntryPointId
    val actionId: Int
    val navGraphRef: Int
    val navArgs: List<Pair<EntryPointParamKey, Any?>>?
}
