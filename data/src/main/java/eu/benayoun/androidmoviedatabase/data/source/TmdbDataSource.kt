package eu.benayoun.androidmoviedatabase.data.source

internal interface TmdbDataSource {
    suspend fun getPopularMovies() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
}