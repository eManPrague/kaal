package cz.eman.kaal.domain.usecases

import cz.eman.kaal.domain.Result


/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This class represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * Use cases are the entry points to the domain layer.
 * @author Roman Holomek (roman.holomek@eman.cz)
 */
abstract class UseCaseResultNoParams<out T : Any> : UseCase<Result<T>, Unit>() {

    suspend operator fun invoke() = super.invoke(Unit)

}
