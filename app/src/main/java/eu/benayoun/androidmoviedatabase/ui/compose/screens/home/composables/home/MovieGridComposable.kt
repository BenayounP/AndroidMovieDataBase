package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.Companion.padding1


@Composable
fun  MovieGridComposable(tmdbMovieList: List<TmdbMovie>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(padding1),
        horizontalArrangement = Arrangement.spacedBy(padding1),
        modifier = Modifier.padding(horizontal = padding1)
    ) {
        items(items = tmdbMovieList){tmdbMovie ->
            MovieItemComposable(tmdbMovie)
        }
    }
}