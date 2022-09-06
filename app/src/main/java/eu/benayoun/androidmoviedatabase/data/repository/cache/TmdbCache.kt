package eu.benayoun.androidmoviedatabase.data.repository.cache

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow


interface TmdbCache {

    // Movie List
    suspend fun getTmdbMovieList(): List<TmdbMovie>
    suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>)

    // Meta Data
    suspend fun saveTmdbOrigin(tmdbOrigin: TmdbOrigin)
    suspend fun loadTmdbOrigin() : Flow<TmdbOrigin>
}