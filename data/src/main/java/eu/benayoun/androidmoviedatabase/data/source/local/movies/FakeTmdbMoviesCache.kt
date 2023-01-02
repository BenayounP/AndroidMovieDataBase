package eu.benayoun.androidmoviedatabase.data.source.local.movies

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTmdbMoviesCache : TmdbMoviesCache {
    private val _movieListStateFlow = MutableStateFlow<List<TmdbMovie>>(listOf())

    override suspend fun getTmdbMovieListFlow(): Flow<List<TmdbMovie>> = _movieListStateFlow

    override suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) {
        _movieListStateFlow.value = movieList
    }
}