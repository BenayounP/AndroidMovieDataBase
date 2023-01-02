package eu.benayoun.androidmoviedatabase.data.model.api

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie

sealed class TmdbAPIResponse(){
    class Success(val tmdbMovieList: List<TmdbMovie>) : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse()
    class Error(val tmdbAPIError: eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError) : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse()
}


