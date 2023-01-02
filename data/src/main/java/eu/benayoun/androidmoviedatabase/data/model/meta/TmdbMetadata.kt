package eu.benayoun.androidmoviedatabase.data.model.meta

data class TmdbMetadata (val tmdbSourceStatus: TmdbSourceStatus = TmdbSourceStatus.None, val lastInternetSuccessTimestamp : Long= INVALID_TIMESTAMP){
    companion object{
        val INVALID_TIMESTAMP: Long = -1
    }
}