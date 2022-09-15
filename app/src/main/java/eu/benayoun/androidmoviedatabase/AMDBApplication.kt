package eu.benayoun.androidmoviedatabase

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

// Yes this class only exists for Hilt compatibility🤷

@HiltAndroidApp
class AMDBApplication: Application()