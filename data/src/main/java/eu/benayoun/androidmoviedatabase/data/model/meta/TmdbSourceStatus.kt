package eu.benayoun.androidmoviedatabase.data.model.meta

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError

// meta data about the movie data

sealed class TmdbSourceStatus{
    object None : TmdbSourceStatus() // used if no data has been fetched
    object Internet : TmdbSourceStatus()
    class Cache(val tmdbAPIError : TmdbAPIError) : TmdbSourceStatus() // used if data is fetched from local cache (and explaining why)
    object SerializationProblem : TmdbSourceStatus() // used for processing problems with serialization

    override fun toString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append(this.javaClass.simpleName)
        if (this is Cache){
            stringBuffer.append(": ${tmdbAPIError.toString()}")
        }
        return stringBuffer.toString()
    }
}