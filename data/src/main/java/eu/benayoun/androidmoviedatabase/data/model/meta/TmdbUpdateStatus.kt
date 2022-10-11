package eu.benayoun.androidmoviedatabase.data.model.meta

sealed class TmdbUpdateStatus() {
    object Updating : TmdbUpdateStatus()
    object Off : TmdbUpdateStatus()
}