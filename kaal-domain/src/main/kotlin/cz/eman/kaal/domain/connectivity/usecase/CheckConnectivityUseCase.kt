package cz.eman.kaal.domain.connectivity.usecase

import cz.eman.kaal.domain.connectivity.repository.ConnectivityRepository
import cz.eman.kaal.domain.usecases.UseCaseNoParams

/**
 *  Use case to check a connectivity to the internet
 *
 * @author eMan a.s.
 */
class CheckConnectivityUseCase(private val connectivityRepository: ConnectivityRepository) :
    UseCaseNoParams<Boolean>() {

    override suspend fun doWork(params: Unit) = connectivityRepository.isOnline()
}
