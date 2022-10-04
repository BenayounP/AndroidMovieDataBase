package eu.benayoun.androidmoviedatabase.data.source.local

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.TmdbMoviesCache
import kotlinx.coroutines.flow.Flow


internal class TmdbCache(val tmdbMoviesCache: TmdbMoviesCache, val tmdbMetaDataCache: TmdbMetaDataCache) {

    // Movie List
    suspend fun getTmdbMovieListFlow(): Flow<List<TmdbMovie>> = tmdbMoviesCache.getTmdbMovieListFlow()
    suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) = tmdbMoviesCache.saveTmdbMovieList(movieList)

    // Meta Data
    suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbMetaDataCache.getTmdbMetaDataFlow()
    suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata) = tmdbMetaDataCache.saveTmdbMetaData(tmdbMetadata)
}