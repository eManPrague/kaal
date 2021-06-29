package cz.eman.kaal.presentation.util

/**
 * Kotlin reimplementation of [androidx.lifecycle.MutableLiveData].
 *
 * @author eMan a.s.
 * @see androidx.lifecycle.MutableLiveData
 * @since 0.9.0
 */
open class KMutableLiveData<T>(value: T) : KLiveData<T>(value) {

    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }
}
