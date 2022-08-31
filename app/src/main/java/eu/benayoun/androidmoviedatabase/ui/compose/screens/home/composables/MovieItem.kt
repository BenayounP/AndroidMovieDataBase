package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import eu.benayoun.androidmoviedatabase.R
import eu.benayoun.androidmoviedatabase.ui.theme.concrete
import eu.benayoun.androidmoviedatabase.ui.theme.concrete3
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

@Composable
fun MovieItem(tmdbMovie: TmdbMovie,
              modifier: Modifier = Modifier) {
    Column() {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(tmdbMovie.posterUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_baseline_movie),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(modifier = Modifier.padding(start = 4.dp),
            text = tmdbMovie.title
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}