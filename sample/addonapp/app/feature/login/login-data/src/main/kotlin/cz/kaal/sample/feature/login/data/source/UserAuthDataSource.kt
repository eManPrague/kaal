package cz.kaal.sample.feature.login.data.source

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.feature.login.domain.model.User

/**
 * @author eMan a.s.
 */
interface UserAuthDataSource {

    suspend fun authorizeUser(user: User): Result<User>

    suspend fun registerUser(user: User): Result<User>

}
