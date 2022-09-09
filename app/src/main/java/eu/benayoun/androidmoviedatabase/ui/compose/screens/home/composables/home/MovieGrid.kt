package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

@Composable
fun  MovieGrid(tmdbMovieList: List<TmdbMovie>,modifier: Modifier = Modifier) {
   val basicPading = 8.dp

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(basicPading),
        horizontalArrangement = Arrangement.spacedBy(basicPading),
        modifier = Modifier.padding(horizontal = basicPading)
    ) {
        items(items = tmdbMovieList){tmdbMovie ->
            MovieItem(tmdbMovie)
        }
    }
}