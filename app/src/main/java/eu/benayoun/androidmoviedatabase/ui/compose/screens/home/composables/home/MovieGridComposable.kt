package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.Companion.padding1


@Composable
fun  MovieGridComposable(tmdbMovieList: List<TmdbMovie>, onOverscroll: () -> Unit) {
    val gridState = rememberLazyGridState()
    updateOnOverScroll(gridState,onOverscroll)

    LazyVerticalGrid(state = gridState, columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(padding1),
        horizontalArrangement = Arrangement.spacedBy(padding1),
        modifier = Modifier.padding(horizontal = padding1)
    ) {
        items(items = tmdbMovieList){tmdbMovie ->
            MovieItemComposable(tmdbMovie)
        }
    }
}

@Composable
fun updateOnOverScroll(gridState : LazyGridState, onOverscroll: () -> Unit){
    val isOverScrolling = isOverScrolling(gridState = gridState)
    if (isOverScrolling){
        onOverscroll()
    }
}

@Composable
private fun isOverScrolling(gridState : LazyGridState) : Boolean {
    val isScrollingDown = gridState.isScrollingDown()
    val firstVisibleItemScrollOffset = gridState.firstVisibleItemScrollOffset
    val isScrolling = gridState.isScrollInProgress
    return firstVisibleItemScrollOffset==0 && isScrolling && !isScrollingDown
}

@Composable
private fun LazyGridState.isScrollingDown(): Boolean {
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            previousScrollOffset < firstVisibleItemScrollOffset
            .also {
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}