package cz.kaal.sample.feature.login.data.repository

import cz.kaal.sample.feature.login.data.source.UserAuthDataSource
import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaal.feature.login.domain.repository.UserAuthRepository

/**
 * @author eMan a.s.
 */
class UserAuthRepositoryImpl(private val userDataSource: UserAuthDataSource) : UserAuthRepository {

    override suspend fun authorizeUser(user: User) = userDataSource.authorizeUser(user)

    override suspend fun registerUser(user: User) = userDataSource.registerUser(user)

}
