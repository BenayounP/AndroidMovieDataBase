package eu.benayoun.androidmoviedatabase.data.model.api

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus

sealed class TmdbAPIError(){
    class NoInternet() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError()
    class ToolError() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // for retrofit errors for example
    class NoData() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // there is no data (no movie) in the response
    class Exception(val localizedMessage: String) : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // if there was some Kotlin exception
    class Unknown() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError() // used for processing problems with serialization

    override fun toString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append(this.javaClass.simpleName)
        if (this is Exception){
            stringBuffer.append(": message: $localizedMessage")
        }
        return stringBuffer.toString()
    }
}