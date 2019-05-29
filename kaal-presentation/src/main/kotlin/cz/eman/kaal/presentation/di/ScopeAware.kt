package cz.eman.kaal.presentation.di

import androidx.lifecycle.Lifecycle

/**
 * @author eMan (vaclav.souhrada@eman.cz)
 */
interface ScopeAware {

    val scopeId: String

    val scopedLifecycleEvent: Lifecycle.Event
        get() = Lifecycle.Event.ON_DESTROY
}