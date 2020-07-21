package cz.kaal.feature.app.core.feature

/**
 * @author eMan a.s.
 * @since 1.0.0
 */
interface FeatureAppRegistration {

    /**
     * Register Feature to application
     * @param[application] the [FeatureApplication] to make an initialization of feature
     *
     * @return true in case of that feature has been initialized, otherwise a false is returned
     * @since 1.0.0
     */
    fun initFeature(application: FeatureApplication): Boolean
}
