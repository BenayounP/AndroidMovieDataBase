package eu.benayoun.androidmoviedatabase.data.model.api

sealed class TmdbAPIError(){
    class NoInternet() : TmdbAPIError()
    class ToolError() : TmdbAPIError() // for retrofit errors for example
    class NoData() : TmdbAPIError() // there is no data (no movie) in the response
    class Exception(val localizedMessage: String) : TmdbAPIError() // if there was some Kotlin exception
    class Unknown() : TmdbAPIError() // used for processing problems with serialization
}