package eu.benayoun.androidmoviedatabase.data.model.api

import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

sealed class TmdbAPIResponse(){
    class Success(val tmdbMovieList: List<TmdbMovie>) : TmdbAPIResponse()
    class Error(val tmdbAPIError: TmdbAPIError) : TmdbAPIResponse()
}


