package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import kotlinx.coroutines.flow.Flow


interface TmdbRepository {
    /***
     * Flows
     */

    // the flow with the popular movies to be displayed
    suspend fun getPopularMovieListFlow() : Flow<List<TmdbMovie>>
    // the flow with metadata:
    // - data sources web or the cache (when online for example)
    // - last internet update
    suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata>

    // a simple flow that indicates if update on internet is on the way
    suspend fun getTmdbUpdateStatusFlow(): Flow<TmdbUpdateStatus>

    /**
     * Update data
     */
    fun updateTmdbMovies()
}