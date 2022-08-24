package eu.benayoun.androidmoviedatabase.data.source.retrofit

import eu.benayoun.androidmoviedatabase.data.source.TMDBDataSource
import eu.pbenayoun.thatdmdbapp.repository.model.TMDBMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitTMDBDataSource: TMDBDataSource {
    private val retrofitPopularMoviesService: RetrofitPopularMoviesService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitPopularMoviesService = retrofit.create(RetrofitPopularMoviesService::class.java)
    }

    override suspend fun getPopularMovies() : List<TMDBMovie>  {
        val response = retrofitPopularMoviesService.getPopularMovies()
        var TMDBMovies : List<TMDBMovie>  = listOf()
        if (response.isSuccessful) {
            val retrofitMovies = response.body()?.retrofitMovies
            if (retrofitMovies!=null){
                TMDBMovies= retrofitMovies.map{
                        retrofitMovie -> retrofitMovie.mapToTMDBMovie()
                }
            }
        }
        return TMDBMovies
    }
}