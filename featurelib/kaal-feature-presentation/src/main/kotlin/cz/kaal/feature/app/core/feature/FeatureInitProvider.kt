package cz.kaal.feature.app.core.feature

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 *
 * @author eMan a.s
 * @see[ContentProvider]
 * @since 1.0.0
 */
abstract class FeatureInitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        context?.let {
            if (it is FeatureAppRegistration) {
                return it.initFeature(provideFeatureApplication())
            }
        }

        return false
    }

    /**
     * Provide the [FeatureApplication] to init and register feature into application
     * @since 1.0.0
     */
    abstract fun provideFeatureApplication(): FeatureApplication

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}
