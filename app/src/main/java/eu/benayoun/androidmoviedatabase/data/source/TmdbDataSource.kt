package eu.benayoun.androidmoviedatabase.data.source

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse

interface TmdbDataSource {
    suspend fun getPopularMovies() : TmdbAPIResponse
}