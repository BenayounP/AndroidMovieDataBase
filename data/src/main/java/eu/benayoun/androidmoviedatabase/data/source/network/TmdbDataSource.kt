package eu.benayoun.androidmoviedatabase.data.source.network

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse

interface TmdbDataSource {
    // get popular movies on the TMDB via internet
    suspend fun getPopularMovies() : TmdbAPIResponse
}