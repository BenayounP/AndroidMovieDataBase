package eu.benayoun.androidmoviedatabase.data.source.local.movies.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie

@Entity(tableName = TmdbDataBase.DB_NAME)
internal class TmdbMovieEntity(
    @NonNull
    @PrimaryKey
    val id: Long,
    val title: String,
    val posterUrl: String,
    val releaseDate: String){

    constructor(tmdbMovie: TmdbMovie) : this(
        id=tmdbMovie.id,
        title = tmdbMovie.title,
        posterUrl = tmdbMovie.posterUrl,
        releaseDate = tmdbMovie.releaseDate
    )

    fun asTmdbMovie(): TmdbMovie {
        return TmdbMovie(
            id = this.id,
            title = this.title,
            posterUrl = this.posterUrl,
            releaseDate = this.releaseDate
        )
    }

}