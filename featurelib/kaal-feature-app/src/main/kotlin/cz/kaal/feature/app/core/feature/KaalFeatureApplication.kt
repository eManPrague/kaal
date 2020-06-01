package cz.kaal.feature.app.core.feature

import android.content.Context
import android.content.ContextWrapper

/**
 * Feature Application class
 *
 * This class is started by host application when the FEATURE is fully loaded. Class is instantiated only in one instance for each feature.
 * @author eMan s.r.o.
 * @constructor Create Feature Application.
 * @property[appContext] application context to wrap.
 * @see[ContextWrapper]
 * @since 1.0.0
 */
abstract class KaalFeatureApplication(open val appContext: Context) :
    ContextWrapper(appContext.applicationContext), FeatureApplication
