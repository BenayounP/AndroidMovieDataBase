package eu.benayoun.androidmoviedatabase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import eu.benayoun.androidmoviedatabase.ui.compose.screens.HomeScreen
import eu.benayoun.androidmoviedatabase.ui.theme.AndroidMovieDataBaseTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityWidth = getActivityWidth()
        setContent {
            AndroidMovieDataBaseTheme {
                HomeScreen(viewWidth = activityWidth)
            }
        }
    }

    private fun getActivityWidth(): Dp {
        val displayMetrics = resources.displayMetrics
        return Dp((displayMetrics.widthPixels / displayMetrics.density))
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidMovieDataBaseTheme {
        HomeScreen(viewWidth = 200.dp)
    }
}