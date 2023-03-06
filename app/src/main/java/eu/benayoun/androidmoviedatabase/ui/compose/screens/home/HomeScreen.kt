package eu.benayoun.androidmoviedatabase.ui.compose.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.MovieGridComposable
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.home.UpdateStatusComposable
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.model.HomeViewModel


// Lifecycle observation found here : https://betterprogramming.pub/jetpack-compose-with-lifecycle-aware-composables-7bd5d6793e0

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), viewWidth: Dp) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        viewModel.ObserveLifecycle(LocalLifecycleOwner.current.lifecycle)

        Column {
            val tmdbMovieList = viewModel.movieListState.collectAsState().value
            fun updateTmdbMovies() = viewModel.updateTmdbMovies()

            UpdateStatusComposable(
                tmdbMetadata = viewModel.tmdbMetadataState.collectAsState().value,
                tmdbUpdateStatus = viewModel.tmdbUpdateStatus.collectAsState().value,
                tmdbMovieList = tmdbMovieList,
                updateMovies = ::updateTmdbMovies
            )

            MovieGridComposable(
                viewWidth = viewWidth,
                tmdbMovieList = tmdbMovieList,
                onPullToRefresh = ::updateTmdbMovies
            )
        }
    }
}

@Composable
fun <LO : LifecycleObserver> LO.ObserveLifecycle(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@ObserveLifecycle)
        onDispose {
            lifecycle.removeObserver(this@ObserveLifecycle)
        }
    }
}