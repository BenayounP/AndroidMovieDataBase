package eu.benayoun.androidmoviedatabase.data.source.retrofit

import com.google.gson.annotations.SerializedName

internal data class RetrofitMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val retrofitMovies: List<RetrofitMovie>,
    @SerializedName("total_pages") val pages: Int
)