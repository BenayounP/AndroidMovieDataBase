package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import eu.benayoun.androidmoviedatabase.R
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.padding2
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.padding3


@Composable
fun MovieItemComposable(
    tmdbMovie: TmdbMovie,
    modifier: Modifier = Modifier,
    posterHeight: Dp
) {
    Column(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
                .height(posterHeight)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tmdbMovie.posterUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_baseline_movie),
                contentDescription = stringResource(R.string.content_description_poster),
                contentScale = ContentScale.FillWidth
            )
        }
        Spacer(modifier = Modifier.padding(padding3))
        Column(
            modifier = modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier.padding(start = padding2),
                text = tmdbMovie.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(start = padding2),
                text = tmdbMovie.releaseDate,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

// not really usable now but you have an sample
@Preview(name = "Preview1", device = Devices.PIXEL_3A, showSystemUi = true, widthDp = 540)
@Composable
fun DefaultPreview() {
    val tmdbMovie = TmdbMovie(
        id = 532639,
        title = "Pinocchio",
        posterUrl = "https://image.tmdb.org/t/p/original/h32gl4a3QxQWNiNaR4Fc1uvLBkV.jpg",
        releaseDate = "2022-09-07"
    )
    MovieItemComposable(tmdbMovie = tmdbMovie, posterHeight = 300.dp)
}