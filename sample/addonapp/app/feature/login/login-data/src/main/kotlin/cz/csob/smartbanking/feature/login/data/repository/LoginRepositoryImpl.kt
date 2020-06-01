package cz.csob.smartbanking.feature.login.data.repository

import cz.csob.smartbanking.feature.login.data.source.LoginDataSource
import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaal.feature.login.domain.repository.LoginRepository

/**
 * @author eMan a.s.
 */
class LoginRepositoryImpl(private val userDataSource: LoginDataSource) : LoginRepository {

    override suspend fun authorizeUser(user: User) = userDataSource.authorizeUser(user)

    override suspend fun registerUser(user: User) = userDataSource.registerUser(user)

}
