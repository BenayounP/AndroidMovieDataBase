package eu.benayoun.androidmoviedatabase.data.source.local.movies

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import kotlinx.coroutines.flow.Flow

internal interface TmdbMoviesCache {
    // get the flow with all popular movies
    suspend fun getTmdbMovieListFlow(): Flow<List<TmdbMovie>>

    // sava data on device "hard drive"
    suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>)
}