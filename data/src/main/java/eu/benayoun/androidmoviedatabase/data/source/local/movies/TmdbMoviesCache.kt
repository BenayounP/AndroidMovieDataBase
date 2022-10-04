package eu.benayoun.androidmoviedatabase.data.source.local.movies

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import kotlinx.coroutines.flow.Flow

internal interface TmdbMoviesCache {
    suspend fun getTmdbMovieListFlow(): Flow<List<TmdbMovie>>
    suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>)
}