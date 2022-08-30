package eu.benayoun.androidmoviedatabase.data.source

import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

sealed class TmdbAPIError(){
    class NoInternet() : TmdbAPIError()
    class ToolError() : TmdbAPIError() // for retrofit errors for example
    class NoData() : TmdbAPIError() // there is no data (no movie) in the response
    class Exception(val localizedMessage: String) : TmdbAPIError() // if there was some Kotlin exception
}


sealed class TmdbAPIResponse(){
    class Success(val tmdbMovieList: List<TmdbMovie>) : TmdbAPIResponse()
    class Error(val tmdbAPIError: TmdbAPIError) : TmdbAPIResponse()
}


