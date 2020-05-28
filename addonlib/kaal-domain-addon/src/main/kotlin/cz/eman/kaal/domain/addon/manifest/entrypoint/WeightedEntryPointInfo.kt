package cz.eman.kaal.domain.addon.manifest.entrypoint

/**
 * @author eMan s.r.o.
 */
interface WeightedEntryPointInfo : EntryPointInfo {
    val orderWeight: Int
}
