package cz.eman.kaal.domain.addon.demo.repository

import cz.eman.kaal.domain.addon.manifest.entrypoint.demo.ApiEntryPointInfo
import cz.eman.kaal.domain.result.Result

/**
 * @author eMan s.r.o.
 */
interface DemoModeRepository {

    fun getDemoApiEntryPointInfo(endpoint: String): Result<ApiEntryPointInfo>
}
