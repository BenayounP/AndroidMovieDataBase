package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

@Composable
fun  MovieGrid(tmdbMovieList: List<TmdbMovie>,modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)
    ) {
        items(items = tmdbMovieList){tmdbMovie ->
            MovieItem(tmdbMovie)
        }
    }
}