package eu.benayoun.androidmoviedatabase

import android.content.Context

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

// An abstract class that :
// * helps to define tests working with robotelectric
// * working on min sdk version
// * give access to context in JVM unit tests

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [BuildConfig.MIN_SDK_VERSION])
abstract class AndroidLocalTest {
    val applicationContext: Context by lazy {
        RuntimeEnvironment.getApplication()
    }
}