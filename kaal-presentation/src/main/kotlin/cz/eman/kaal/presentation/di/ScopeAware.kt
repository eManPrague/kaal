package cz.eman.kaal.presentation.di

import androidx.lifecycle.Lifecycle

/**
 * @author eMan (vaclav.souhrada@eman.cz)
 * @since 0.1.0
 */
interface ScopeAware {

    val scopeId: String

    val scopedLifecycleEvent: Lifecycle.Event
        get() = Lifecycle.Event.ON_DESTROY
}