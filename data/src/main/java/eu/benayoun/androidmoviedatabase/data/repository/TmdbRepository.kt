package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import kotlinx.coroutines.flow.Flow


interface TmdbRepository {

    // Flows
    suspend fun getPopularMovieListFlow() : Flow<List<TmdbMovie>>
    suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata>
    suspend fun getTmdbUpdateStatusFlow(): Flow<TmdbUpdateStatus>

    // update data
    fun updateTmdbMovies()

}