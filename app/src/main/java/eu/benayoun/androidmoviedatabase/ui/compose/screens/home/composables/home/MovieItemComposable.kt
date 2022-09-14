package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import eu.benayoun.androidmoviedatabase.R
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.Companion.padding1
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.Companion.padding2


@Composable
fun MovieItemComposable(tmdbMovie: TmdbMovie,
                        modifier: Modifier = Modifier) {
    Column() {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(tmdbMovie.posterUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_baseline_movie),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(padding1))
        Text(modifier = Modifier.padding(start = padding2),
            text = tmdbMovie.title
        )
        Spacer(modifier = Modifier.height(padding1))
    }
}