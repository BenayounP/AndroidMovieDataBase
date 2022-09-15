package eu.benayoun.androidmoviedatabase.data.source.network.retrofit

import com.google.gson.annotations.SerializedName

internal data class RetrofitMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val retrofitTmdbMovies: List<RetrofitTmdbMovie>,
    @SerializedName("total_pages") val pages: Int
)