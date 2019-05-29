package cz.eman.kaal

import android.os.Bundle
import cz.eman.kaal.presentation.activity.BaseActivity
import cz.eman.kaal.sample.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
