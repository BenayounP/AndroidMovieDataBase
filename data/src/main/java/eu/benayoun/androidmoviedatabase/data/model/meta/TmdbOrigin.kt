package eu.benayoun.androidmoviedatabase.data.model.meta

// meta data about the movie data

sealed class TmdbOrigin(){
    class Internet() : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin()
    class Cache(val tmdbAPIError : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError) : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin()
    class Unknown() : eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin() // used for processing problems with serialization
}