package cz.kaal.feature.app.core.feature

/**
 * Feature Application
 *
 * @author eMan a.s
 * @since 1.0.0
 */
interface FeatureApplication {

    /**
     * Initial configuration of AddonApplication like DI etc.
     *
     * @since 1.0.0
     */
    fun init()
}
