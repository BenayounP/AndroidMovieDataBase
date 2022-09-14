package eu.benayoun.androidmoviedatabase.data.model.meta

data class TmdbMetadata (val tmdbOrigin: eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin = eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin.Unknown(), val lastInternetSuccessTimeStamp : Long=-1)