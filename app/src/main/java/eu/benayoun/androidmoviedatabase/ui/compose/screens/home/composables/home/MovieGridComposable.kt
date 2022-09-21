package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.ui.theme.ComposeDimensions.Companion.padding1


@Composable
fun  MovieGridComposable(tmdbMovieList: List<TmdbMovie>, onPullToRefresh: () -> Unit) {
    val gridState = rememberLazyGridState()
    updateOnPullToRefresh(gridState,onPullToRefresh)
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(padding1),
        horizontalArrangement = Arrangement.spacedBy(padding1),
        modifier = Modifier
            .padding(horizontal = padding1),
        state = gridState
    ) {

        items(items = tmdbMovieList){tmdbMovie ->
            MovieItemComposable(tmdbMovie)
        }
    }
}


@Composable
fun updateOnPullToRefresh(gridState : LazyGridState, onPullToRefresh: () -> Unit){
    if (gridState.isPullingToRefresh()){
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
    // LogUtils.v("******************************************")
    val firstItemIsDisplayed = firstVisibleItemIndex ==0
    val isStuckOnTop = firstItemIsDisplayed && currentScrollOffset==0 && oldScrollOffset ==0
    // LogUtils.v("isStuckOnTop:$firstItemIsDisplayed|$oldScrollOffset|$currentScrollOffset -> $isStuckOnTop")
    val isBeginningToPull= isScrolling && !wasScrolling
    // LogUtils.v("isBeginningToPull:$wasScrolling|$isScrolling -> $isBeginningToPull")
    val isPullingToRefresh = isStuckOnTop && isBeginningToPull
    // LogUtils.v("isPullingToRefresh: $isPullingToRefresh")

    // update values
    oldScrollOffset = currentScrollOffset
    wasScrolling = isScrolling
    return isPullingToRefresh

}