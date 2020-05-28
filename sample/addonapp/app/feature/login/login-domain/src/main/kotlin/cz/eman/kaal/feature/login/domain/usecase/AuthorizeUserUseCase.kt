package cz.eman.kaal.feature.login.domain.usecase

import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaal.feature.login.domain.repository.LoginRepository

/**
 * @author eMan s.r.o.
 */
class AuthorizeUserUseCase(private val loginRepository: LoginRepository) :
    UseCaseResult<User, AuthorizeUserUseCase.Params>() {

    override suspend fun doWork(params: Params) = loginRepository.authorizeUser(user = params.user)

    data class Params(val user: User)
}
