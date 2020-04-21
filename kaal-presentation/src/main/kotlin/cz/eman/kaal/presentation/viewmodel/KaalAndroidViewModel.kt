package cz.eman.kaal.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.koin.core.KoinComponent

/**
 * @author eMan s.r.o (info@eman.cz)
 * @see[AndroidViewModel]
 * @see[KoinComponent]
 */
abstract class KaalAndroidViewModel(application: Application) : AndroidViewModel(application), KoinComponent
