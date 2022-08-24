package eu.pbenayoun.thatdmdbapp.repository.model


val tmdbPosterPathprefix = "https://image.tmdb.org/t/p/original"

data class TmdbMovie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: String)
