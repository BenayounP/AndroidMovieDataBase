package eu.benayoun.androidmoviedatabase.data.repository

import eu.benayoun.androidmoviedatabase.data.source.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.source.TmdbAPIResponse
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import kotlinx.coroutines.flow.Flow

sealed class DataOrigin(){
    class Internet() : DataOrigin()
    class Cache(val tmdbAPIError : TmdbAPIError) : DataOrigin()
}

class TmdbMetaMovieList(val movieList: List<TmdbMovie>, val dataOrigin : DataOrigin )

interface TmdbRepository {
    suspend fun getPopularMovieListFlow() : Flow<TmdbMetaMovieList>
}