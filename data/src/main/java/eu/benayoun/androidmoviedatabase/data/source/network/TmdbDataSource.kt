package eu.benayoun.androidmoviedatabase.data.source.network

interface TmdbDataSource {
    suspend fun getPopularMovies() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
}