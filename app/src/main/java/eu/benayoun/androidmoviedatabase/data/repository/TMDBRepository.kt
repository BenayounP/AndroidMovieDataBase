package eu.benayoun.androidmoviedatabase.data.repository

import eu.pbenayoun.thatdmdbapp.repository.model.TMDBMovie
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {
    suspend fun getPopularMoviesFlow() : Flow<List<TMDBMovie>>
}