package eu.benayoun.androidmoviedatabase.data.model.meta

data class TmdbMetadata (val tmdbSourceStatus: TmdbSourceStatus = TmdbSourceStatus.None(), val lastInternetSuccessTimeStamp : Long=-1)