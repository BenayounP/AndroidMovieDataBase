package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow


interface TmdbRepository {

    // Movies
    suspend fun getPopularMovieListFlow() : Flow<List<TmdbMovie>>

    // Meta Data
    suspend fun getTmdbMetaDataFlow(): Flow<TmdbMetadata>
}