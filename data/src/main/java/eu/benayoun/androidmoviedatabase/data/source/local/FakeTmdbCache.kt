package eu.benayoun.androidmoviedatabase.data.source.local

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTmdbCache : TmdbCache {
    private val _movieListStateFlow = MutableStateFlow<List<TmdbMovie>>(listOf())
    private val _tmdbMetaDataFlow = MutableStateFlow<TmdbMetadata>(TmdbMetadata())

    override suspend fun getTmdbMovieListFlow(): Flow<List<TmdbMovie>> = _movieListStateFlow

    override suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) {
        _movieListStateFlow.value = movieList
    }

    override fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = _tmdbMetaDataFlow

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata) {
        _tmdbMetaDataFlow.value=tmdbMetadata
    }
}