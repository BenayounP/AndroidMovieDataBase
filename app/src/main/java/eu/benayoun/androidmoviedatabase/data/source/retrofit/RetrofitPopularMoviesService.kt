package eu.benayoun.androidmoviedatabase.data.source.retrofit

import eu.benayoun.androidmoviedatabase.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPopularMoviesService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int =1
    ): Response<RetrofitMoviesResponse>
}