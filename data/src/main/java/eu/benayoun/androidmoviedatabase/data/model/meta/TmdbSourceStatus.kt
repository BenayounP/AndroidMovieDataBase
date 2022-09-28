package eu.benayoun.androidmoviedatabase.data.model.meta

import android.os.Build.VERSION_CODES.S
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError

// meta data about the movie data

sealed class TmdbSourceStatus(){
    class None() : TmdbSourceStatus() // used if no data has been fetched
    class Internet() :TmdbSourceStatus()
    class Cache(val tmdbAPIError : TmdbAPIError) : TmdbSourceStatus()
    class Unknown() : TmdbSourceStatus() // used for processing problems with serialization

    override fun toString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append(this.javaClass.simpleName)
        if (this is Cache){
            stringBuffer.append(": ${tmdbAPIError.toString()}")
        }
        return stringBuffer.toString()
    }
}