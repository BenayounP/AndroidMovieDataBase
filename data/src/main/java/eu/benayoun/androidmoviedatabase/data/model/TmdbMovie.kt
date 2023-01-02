package eu.benayoun.androidmoviedatabase.data.model

val tmdbPosterPathprefix = "https://image.tmdb.org/t/p/original"


// the core of the model, data class about movies!
data class TmdbMovie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: String)
