package eu.benayoun.androidmoviedatabase.data.source

import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

interface TmdbDataSource {
    suspend fun getPopularMovies() : List<TmdbMovie>
}