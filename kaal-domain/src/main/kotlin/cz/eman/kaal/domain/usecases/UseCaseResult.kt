package cz.eman.kaal.domain.usecases

import cz.eman.kaal.domain.result.Result

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This class represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * Use cases are the entry points to the domain layer.
 *
 * @author [eMan a.s.](mailto:info@eman.cz)
 * @since 1.0.0
 */
abstract class UseCaseResult<out T : Any, in Params> : UseCase<Result<T>, Params>()
