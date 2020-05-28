package cz.eman.kaal.domain.addon.manifest.entrypoint

/**
 * Extension of the [EntryPointInfo] which adding caption to entry point.
 *
 * It should be extended by all entry points which requires [label] and [icon].
 * @author eMan s.r.o.
 * @see[EntryPointInfo]
 */
interface CaptionEntryPointInfo : WeightedEntryPointInfo {

    val label: Int
    val icon: Int?
    val iconDescription: Int?
}
