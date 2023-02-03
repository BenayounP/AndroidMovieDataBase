package eu.benayoun.androidmoviedatabase.ui

import android.content.res.Resources
import androidx.compose.ui.unit.Dp

object UIUtils {
    fun getScreenWidth(resources: Resources): Dp {
        val displayMetrics = resources.displayMetrics
        return Dp((displayMetrics.widthPixels / displayMetrics.density))
    }
}