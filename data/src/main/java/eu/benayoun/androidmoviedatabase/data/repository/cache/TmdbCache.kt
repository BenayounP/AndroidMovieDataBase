package eu.benayoun.androidmoviedatabase.data.repository.cache

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import kotlinx.coroutines.flow.Flow


internal interface TmdbCache {

    // Movie List
    suspend fun getTmdbMovieList(): List<TmdbMovie>
    suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>)

    // Meta Data
    fun getTmdbMetaDataFlow(): Flow<TmdbMetadata>
    suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata)
}