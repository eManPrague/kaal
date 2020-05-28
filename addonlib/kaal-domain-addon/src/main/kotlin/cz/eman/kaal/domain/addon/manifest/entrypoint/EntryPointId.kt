package cz.eman.kaal.domain.addon.manifest.entrypoint

/**
 * @author eMan s.r.o.
 */
data class EntryPointId(val addonId: String, val id: Int) {

    fun getComposedId() = "$addonId-$id"
}
