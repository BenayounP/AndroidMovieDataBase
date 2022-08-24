package eu.benayoun.androidmoviedatabase.data.source.retrofit

import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitTmdbDataSource: TmdbDataSource {
    private val retrofitPopularMoviesService: RetrofitPopularMoviesService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitPopularMoviesService = retrofit.create(RetrofitPopularMoviesService::class.java)
    }

    override suspend fun getPopularMovies() : List<TmdbMovie>  {
        val response = retrofitPopularMoviesService.getPopularMovies()
        var TmdbMovies : List<TmdbMovie>  = listOf()
        if (response.isSuccessful) {
            val retrofitMovies = response.body()?.retrofitMovies
            if (retrofitMovies!=null){
                TmdbMovies= retrofitMovies.map{
                        retrofitMovie -> retrofitMovie.mapToTmdbMovie()
                }
            }
        }
        return TmdbMovies
    }
}