package cz.eman.kaal.presentation.util

import androidx.lifecycle.LiveData

/**
 * Kotlin reimplementation of [LiveData]. Adds support for nonnull / nullable generics argument.
 * The implementation doesn't support uninitialized liveData (empty constructor not provided).
 *
 * @author eMan a.s.
 * @see LiveData
 * @since 0.9.0
 */
@Suppress("RedundantOverride") // needs to override methods to enforce type in offspring
abstract class KLiveData<T>(value: T) : LiveData<T>(value) {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(): T {
        return super.getValue() as T
    }

    override fun setValue(value: T) {
        super.setValue(value)
    }

    override fun postValue(value: T) {
        super.postValue(value)
    }
}
