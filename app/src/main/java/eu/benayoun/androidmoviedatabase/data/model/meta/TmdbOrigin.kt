package eu.benayoun.androidmoviedatabase.data.model.meta

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError

// meta data about the movie data

sealed class TmdbOrigin(){
    class Internet() : TmdbOrigin()
    class Cache(val tmdbAPIError : TmdbAPIError) : TmdbOrigin()
    class Unknown() : TmdbOrigin() // used for processing problems with serialization
}