package cz.csob.smartbanking.feature.login.data.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.feature.login.domain.model.User

/**
 * @author eMan s.r.o.
 */
interface LoginDataSource {

    suspend fun authorizeUser(user: User): Result<User>

    suspend fun registerUser(user: User): Result<User>

}
