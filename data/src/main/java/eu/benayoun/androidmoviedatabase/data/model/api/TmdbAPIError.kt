package eu.benayoun.androidmoviedatabase.data.model.api

sealed class TmdbAPIError(){
    object NoInternet : TmdbAPIError()
    object ToolError : TmdbAPIError() // for retrofit errors for example
    object NoData : TmdbAPIError() // there is no data (no movie) in the response
    class Exception(val localizedMessage: String) : TmdbAPIError() // if there was some Kotlin exception
    object Unknown : TmdbAPIError() // used for processing problems with serialization

    override fun toString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append(this.javaClass.simpleName)
        if (this is Exception){
            stringBuffer.append(": message: $localizedMessage")
        }
        return stringBuffer.toString()
    }
}