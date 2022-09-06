package eu.benayoun.androidmoviedatabase.data.model.meta

data class TmdbMetadata (val tmdbOrigin: TmdbOrigin = TmdbOrigin.Unknown(), val lastInternetSuccessTimeStamp : Long=-1)