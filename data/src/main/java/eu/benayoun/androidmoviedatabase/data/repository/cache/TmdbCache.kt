package eu.benayoun.androidmoviedatabase.data.repository.cache

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import kotlinx.coroutines.flow.Flow


interface TmdbCache {

    // Movie List
    suspend fun getTmdbMovieList(): List<TmdbMovie>
    suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>)

    // Meta Data
    fun getTmdbMetaDataFlow(): Flow<eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata>
    suspend fun saveTmdbMetaData(tmdbMetadata: eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata)
}