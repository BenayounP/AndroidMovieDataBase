package eu.benayoun.androidmoviedatabase.ui.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.HomeViewModel
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.MovieGrid
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.home.OriginStatus

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    viewModel.getPopularMoviesFlow()
    viewModel.getTmdbOriginFlow()
    Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
    ) {
        Column(){
            OriginStatus(tmdbMetadata = viewModel.tmdbMetadataState.collectAsState().value)
            MovieGrid(tmdbMovieList = viewModel.movieListState.collectAsState().value)
        }

    }
}