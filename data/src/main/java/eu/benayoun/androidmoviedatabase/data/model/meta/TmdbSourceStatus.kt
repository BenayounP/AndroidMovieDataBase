package eu.benayoun.androidmoviedatabase.data.model.meta

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError

// meta data about the movie data

sealed class TmdbSourceStatus(){
    class None() : TmdbSourceStatus() // used if no data has been processed yet
    class Internet() :TmdbSourceStatus()
    class Cache(val tmdbAPIError : TmdbAPIError) : TmdbSourceStatus()
    class Unknown() : TmdbSourceStatus() // used for processing problems with serialization
}