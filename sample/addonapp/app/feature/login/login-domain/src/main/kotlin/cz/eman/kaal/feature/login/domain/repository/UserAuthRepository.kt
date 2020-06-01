package cz.eman.kaal.feature.login.domain.repository

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.feature.login.domain.model.User

/**
 * @author eMan s.r.o.
 */
interface UserAuthRepository {

    suspend fun authorizeUser(user: User): Result<User>

    suspend fun registerUser(user: User): Result<User>
}
