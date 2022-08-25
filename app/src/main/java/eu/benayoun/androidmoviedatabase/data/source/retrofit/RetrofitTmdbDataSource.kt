package eu.benayoun.androidmoviedatabase.data.source.retrofit

import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
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
        LogUtils.e("---------- retrofit step1")
        var TmdbMovies : List<TmdbMovie>  = listOf()
        try {
            val response = retrofitPopularMoviesService.getPopularMovies()
            if (response.isSuccessful) {
                LogUtils.e("---------- retrofit step3")
                val retrofitMovies = response.body()?.retrofitMovies
                if (retrofitMovies!=null){
                    LogUtils.e("---------- retrofit step4")
                    TmdbMovies= retrofitMovies.map{
                            retrofitMovie -> retrofitMovie.mapToTmdbMovie()
                    }
                }
            }
        }  catch (e: Exception) {
            LogUtils.e("retrofit exception: ${e.localizedMessage}.")
        }

        LogUtils.e("---------- retrofit end")
        return TmdbMovies
    }
}