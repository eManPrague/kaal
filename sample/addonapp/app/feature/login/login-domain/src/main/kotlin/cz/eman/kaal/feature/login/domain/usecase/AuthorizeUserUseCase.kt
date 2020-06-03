package cz.eman.kaal.feature.login.domain.usecase

import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaal.feature.login.domain.repository.UserAuthRepository

/**
 * @author eMan s.r.o.
 */
class AuthorizeUserUseCase(private val userRepository: UserAuthRepository) :
    UseCaseResult<User, AuthorizeUserUseCase.Params>() {

    override suspend fun doWork(params: Params) = userRepository.authorizeUser(user = params.user)

    data class Params(val user: User)
}
