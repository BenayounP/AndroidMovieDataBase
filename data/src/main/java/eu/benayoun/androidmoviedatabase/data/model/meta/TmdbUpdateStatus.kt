package eu.benayoun.androidmoviedatabase.data.model.meta

sealed class TmdbUpdateStatus() {
    class Updating() : TmdbUpdateStatus()
    class Off() : TmdbUpdateStatus()
}