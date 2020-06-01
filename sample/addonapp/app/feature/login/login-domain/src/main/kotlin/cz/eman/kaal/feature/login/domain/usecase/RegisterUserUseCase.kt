package cz.eman.kaal.feature.login.domain.usecase

import cz.eman.kaal.domain.usecases.UseCaseResult
import cz.eman.kaal.feature.login.domain.model.User
import cz.eman.kaal.feature.login.domain.repository.UserAuthRepository

/**
 * @author eMan s.r.o.
 */
class RegisterUserUseCase(private val userRepository: UserAuthRepository) :
    UseCaseResult<User, RegisterUserUseCase.Params>() {

    override suspend fun doWork(params: Params) = userRepository.registerUser(user = params.user)

    data class Params(val user: User)
}