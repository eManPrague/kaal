@file:Suppress("unused")

package cz.eman.kaal.domain.result

/**
 * Extensions for Result class.
 *
 * @author: eMan a.s.
 * @since 0.9.0
 */

/**
 * Maps success part of the Result using a [mapAction]. Keeps error as it is. This should be used only to handle / map
 * the result object to another. It should not be used to chain other functions. For that there is a [chain] function.
 *
 * @param mapAction map function for body of [Result.Success] branch
 * @return [Result] with [S]
 * @since 0.9.0
 */
inline fun <T, S> Result<T>.map(mapAction: (T) -> S): Result<S> {
    return when (this) {
        is Result.Success -> Result.Success(mapAction(this.data))
        is Result.Error -> Result.error(this.error, null)
    }
}

/**
 * Maps success part of the Result using a [mapAction]. Keeps error as it is. This should be used only to handle / map
 * the result object to another. It should not be used to chain other functions. For that there is a [chain] function.
 *
 * @param mapAction map function for [Result.Success] branch
 * @return [Result] with [S]
 * @since 0.9.0
 */
inline fun <T, S> Result<T>.mapResult(mapAction: (T) -> Result<S>): Result<S> {
    return when (this) {
        is Result.Success -> mapAction(this.data)
        is Result.Error -> Result.error(this.error, null)
    }
}

/**
 * Chains an action on the success of the current Result. Use to call other functions on success of this this [Result].
 * Does not call the the [chainAction] on error.
 *
 * @param chainAction chain function for [Result.Success]
 * @return [Result] with [S]
 * @since 0.9.0
 */
inline infix fun <T, S> Result<T>.chain(chainAction: (T) -> Result<S>): Result<S> {
    return when (this) {
        is Result.Success -> chainAction(this.data)
        is Result.Error -> Result.error(this.error, null)
    }
}

/**
 * Retypes [Result.Error] to a [Result.Error] with new error code. Keeps the message and throwable the same.
 *
 * @param newErrorCode to be retyped to
 * @return [Result] with [S]
 * @since 0.9.0
 */
fun <T, S> Result.Error<T>.retypeErrorCode(newErrorCode: ErrorCode? = null): Result<S> =
    Result.error(
        ErrorResult(
            code = newErrorCode ?: this.error.code,
            message = this.error.message,
            throwable = this.error.throwable
        ), null
    )

/**
 * Runs a [block] function after the result finishes. Returns this to allow chaining of additional functions.
 *
 * @param block of code to run
 * @return [Result] with [T] which the same as call [Result]
 * @since 0.9.0
 */
fun <T> Result<T>.onFinished(block: (Result<T>) -> Unit): Result<T> {
    block(this)
    return this
}

/**
 * Runs a [block] function only when current [Result] is successful. Returns this to allow chaining of additional
 * functions.
 *
 * @param block of code to run on success
 * @return [Result] with [T] which the same as call [Result]
 * @since 0.9.0
 */
inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    if (this is Result.Success<T>) {
        block(this.data)
    }
    return this
}

/**
 * Runs a [block] function only when current [Result] fails with an error. Returns this to allow chaining of additional
 * functions.
 *
 * @param block of code to run on error
 * @return [Result] which the same as call [Result]
 * @since 0.9.0
 */
inline fun <T> Result<T>.onError(block: (ErrorResult) -> Unit): Result<T> {
    if (this is Result.Error<T>) {
        block(this.error)
    }
    return this
}

/**
 * Converts object [T] into [Result.Success] containing [T] only when calling object is not null. Else it builds
 * [ErrorResult] and fills [Result.error] with it.
 *
 * @param error used to build [ErrorResult] when predicate is false
 * @return [Result] with [T] calling object
 * @see toSuccessIfPredicate
 * @since 0.9.0
 */
inline fun <T : Any> T?.toSuccessIfNotNull(error: () -> ErrorResult): Result<T> =
    toSuccessIfPredicate(
        error = error,
        predicate = { this != null }
    )

/**
 * Converts object [T] into [Result.Success] containing [T] only when [predicate] returns true. Else it builds
 * [ErrorResult] and fills [Result.error] with it.
 *
 * @param predicate condition to check before wrapping the object in [Result.Success]
 * @param error used to build [ErrorResult] when predicate is false
 * @return [Result] with [T] calling object
 * @since 0.9.0
 */
inline fun <T : Any> T?.toSuccessIfPredicate(
    predicate: (T?) -> Boolean,
    error: () -> ErrorResult
): Result<T> {
    return if (this != null && predicate(this)) {
        this.toSuccess()
    } else {
        Result.error(error())
    }
}

/**
 * Maps any object to [Result.Success] containing this object.
 *
 * @return [Result.Success] with [T] calling object
 * @since 0.9.0
 */
fun <T : Any> T.toSuccess(): Result<T> {
    return Result.success(this)
}

/**
 * Combines two successful results ([T1], [T2]) into one single result [R]. If any of the two result is a [Result.Error]
 * then it returns it to handle the error state.
 *
 * @param other second [Result] to combine with the first one
 * @param transform function combining two results
 * @return [Result] with [R] data
 * @see 0.9.0
 */
inline fun <T1 : Any, T2 : Any, R : Any> Result<T1>.combine(
    other: Result<T2>,
    transform: (T1, T2) -> R
): Result<R> {
    return when {
        this is Result.Error<*> -> this.retypeErrorCode()
        other is Result.Error<*> -> other.retypeErrorCode()
        else -> Result.Success(
            transform(
                (this as Result.Success).data,
                (other as Result.Success).data
            )
        )
    }
}

/**
 * Combines three successful results ([T1], [T2], [T3]) into one single result [R]. If any of the three result is a
 * [Result.Error] then it returns it to handle the error state.
 *
 * @param two second [Result] to combine with the other ones
 * @param three third [Result] to combine with the other ones
 * @param transform function combining three results
 * @return [Result] with [R] data
 * @see 0.9.0
 */
inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> Result<T1>.combine(
    two: Result<T2>,
    three: Result<T3>,
    transform: (T1, T2, T3) -> R
): Result<R> {
    return when {
        this is Result.Error<*> -> this.retypeErrorCode()
        two is Result.Error<*> -> two.retypeErrorCode()
        three is Result.Error<*> -> three.retypeErrorCode()
        else -> Result.Success(
            transform(
                (this as Result.Success).data,
                (two as Result.Success).data,
                (three as Result.Success).data
            )
        )
    }
}

/**
 * Combines four successful results ([T1], [T2], [T3], [T4]) into one single result [R]. If any of the four result is a
 * [Result.Error] then it returns it to handle the error state.
 *
 * @param two second [Result] to combine with the other ones
 * @param three third [Result] to combine with the other ones
 * @param four fourth [Result] to combine with the other ones
 * @param transform function combining four results
 * @return [Result] with [R] data
 * @see 0.9.0
 */
inline fun <T1 : Any?, T2 : Any, T3 : Any, T4 : Any, R : Any> Result<T1>.combine(
    two: Result<T2>,
    three: Result<T3>,
    four: Result<T4>,
    transform: (T1, T2, T3, T4) -> R
): Result<R> {
    return when {
        this is Result.Error<*> -> this.retypeErrorCode()
        two is Result.Error<*> -> two.retypeErrorCode()
        three is Result.Error<*> -> three.retypeErrorCode()
        four is Result.Error<*> -> four.retypeErrorCode()
        else -> Result.Success(
            transform(
                (this as Result.Success).data,
                (two as Result.Success).data,
                (three as Result.Success).data,
                (four as Result.Success).data
            )
        )
    }
}
