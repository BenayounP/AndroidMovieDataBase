package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.padding1


@Composable
fun MovieGridComposable(
    modifier: Modifier = Modifier,
    viewWidth: Dp,
    tmdbMovieList: List<TmdbMovie>,
    onPullToRefresh: () -> Unit
) {
    val itemWidth = (viewWidth - padding1) / 2
    // typical movie poster ratio is 3/2
    val posterHeight = itemWidth * 3 / 2
    val gridState = rememberLazyGridState()
    UpdateOnPullToRefresh(gridState, onPullToRefresh)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(padding1),
        horizontalArrangement = Arrangement.spacedBy(padding1),
        modifier = modifier
            .padding(horizontal = padding1),
        state = gridState
    ) {
        items(items = tmdbMovieList) { tmdbMovie ->
            MovieItemComposable(tmdbMovie, posterHeight = posterHeight)
        }
    }
}


@Composable
fun UpdateOnPullToRefresh(gridState: LazyGridState, onPullToRefresh: () -> Unit) {
    if (gridState.isPullingToRefresh()) {
        onPullToRefresh()
    }
}


@Composable
private fun LazyGridState.isPullingToRefresh(): Boolean {
    val currentScrollOffset = firstVisibleItemScrollOffset
    val isScrolling = isScrollInProgress

    var oldScrollOffset by remember(this) { mutableStateOf(currentScrollOffset) }
    var wasScrolling by remember(this) { mutableStateOf(false) }

    //tests
    val firstItemIsDisplayed = firstVisibleItemIndex ==0
    val isStuckOnTop = firstItemIsDisplayed && currentScrollOffset==0 && oldScrollOffset ==0
    val isBeginningToPull= isScrolling && !wasScrolling
    val isPullingToRefresh = isStuckOnTop && isBeginningToPull

    // update values
    oldScrollOffset = currentScrollOffset
    wasScrolling = isScrolling
    return isPullingToRefresh
}