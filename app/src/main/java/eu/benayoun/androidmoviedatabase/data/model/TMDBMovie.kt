package eu.pbenayoun.thatdmdbapp.repository.model


val TMDBposterPathprefix = "https://image.tmdb.org/t/p/original"

data class TMDBMovie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: String,
    var userRating:Int=-1)
