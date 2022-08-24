package eu.benayoun.androidmoviedatabase.data.source

import eu.benayoun.androidmoviedatabase.data.source.retrofit.RetrofitMovie
import eu.pbenayoun.thatdmdbapp.repository.model.TMDBMovie

interface TMDBDataSource {
    suspend fun getPopularMovies() : List<TMDBMovie>
}