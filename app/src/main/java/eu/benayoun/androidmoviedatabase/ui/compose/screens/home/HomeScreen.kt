package eu.benayoun.androidmoviedatabase.ui.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.HomeViewModel
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.MovieGridComposable
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.home.UpdateStatusComposable


// Lifecycle observation found here : https://betterprogramming.pub/jetpack-compose-with-lifecycle-aware-composables-7bd5d6793e0

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
    ) {
        viewModel.observeLifecycle(LocalLifecycleOwner.current.lifecycle)

        Column(){
            UpdateStatusComposable(tmdbMetadata = viewModel.tmdbMetadataState.collectAsState().value, tmdbUpdateStatus = viewModel.tmdbUpdateStatus.collectAsState().value)
            fun onPullToRefresh()= viewModel.updateTmdbMovies()
            MovieGridComposable(tmdbMovieList = viewModel.movieListState.collectAsState().value,::onPullToRefresh)
        }
    }
}

@Composable
fun <LO : LifecycleObserver> LO.observeLifecycle(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@observeLifecycle)
        onDispose {
            lifecycle.removeObserver(this@observeLifecycle)
        }
    }
}