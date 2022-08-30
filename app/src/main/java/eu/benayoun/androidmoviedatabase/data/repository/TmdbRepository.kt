package eu.benayoun.androidmoviedatabase.data.repository

import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow

interface TmdbRepository {
    suspend fun getPopularMovieListFlow() : Flow<List<TmdbMovie>>
}