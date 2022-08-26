package eu.benayoun.androidmoviedatabase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import eu.benayoun.androidmoviedatabase.ui.compose.screens.HomeScreen
import eu.benayoun.androidmoviedatabase.ui.theme.AndroidMovieDataBaseTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidMovieDataBaseTheme {
                HomeScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidMovieDataBaseTheme {
        HomeScreen()
    }
}