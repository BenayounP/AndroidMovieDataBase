package eu.benayoun.androidmoviedatabase.data.model.api

sealed class TmdbAPIError(){
    class NoInternet() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError()
    class ToolError() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // for retrofit errors for example
    class NoData() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // there is no data (no movie) in the response
    class Exception(val localizedMessage: String) : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // if there was some Kotlin exception
    class Unknown() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // used for processing problems with serialization
}