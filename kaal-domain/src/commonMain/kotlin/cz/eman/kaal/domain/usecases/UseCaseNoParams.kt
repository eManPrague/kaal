package cz.eman.kaal.domain.usecases

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This class represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * Use cases are the entry points to the domain layer.
 * @author Roman Holomek (roman.holomek@eman.cz)
 * @since 1.0.0
 */
abstract class UseCaseNoParams<out T> : UseCase<T, Unit>() {

    suspend operator fun invoke(): T = super.invoke(Unit)
}
