package eu.benayoun.androidmoviedatabase.data.source.retrofit

import com.google.gson.annotations.SerializedName
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import eu.pbenayoun.thatdmdbapp.repository.model.tmdbPosterPathprefix

data class RetrofitMovie(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String
){
    fun mapToTmdbMovie() : TmdbMovie{
        return TmdbMovie(id,title,tmdbPosterPathprefix+posterPath,releaseDate)
    }
}