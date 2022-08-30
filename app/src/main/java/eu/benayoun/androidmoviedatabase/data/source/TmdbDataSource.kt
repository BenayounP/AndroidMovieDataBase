package eu.benayoun.androidmoviedatabase.data.source

interface TmdbDataSource {
    suspend fun getPopularMovies() : TmdbAPIResponse
}