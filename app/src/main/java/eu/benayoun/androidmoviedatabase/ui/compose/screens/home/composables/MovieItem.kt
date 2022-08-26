package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

@Composable
fun MovieItem(tmdbMovie: TmdbMovie,
              modifier: Modifier = Modifier) {
    Card(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(
            modifier = modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                text = tmdbMovie.title
            )
        }
    }
}