package eu.benayoun.androidmoviedatabase.data.source.network.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal class RetrofitTmdbDataSource(val context: Context): TmdbDataSource {
    private val retrofitPopularMoviesService: RetrofitPopularMoviesService

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitPopularMoviesService = retrofit.create(RetrofitPopularMoviesService::class.java)
    }

    override suspend fun getPopularMovies() : eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse {
        LogUtils.v("    Retrofit step 1: check internet connectivity")
        if (!isNetworkConnected(context)){
            return eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse.Error(
                TmdbAPIError.NoInternet)
        }
        LogUtils.v("    Retrofit step 2: get movies via API")
        try {
            val response = retrofitPopularMoviesService.getPopularMovies()
            LogUtils.v("    Retrofit step 3: process response")
            if (response.isSuccessful) {
                val retrofitMovies = response.body()?.retrofitTmdbMovies
                if (retrofitMovies!=null){
                    return eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse.Success(retrofitMovies.map{
                            retrofitMovie -> retrofitMovie.asTmdbMovie()
                    })
                }
                else {
                    return TmdbAPIResponse.Error(TmdbAPIError.NoData)
                }
            }
            else{
                return TmdbAPIResponse.Error(TmdbAPIError.ToolError)
            }
        }  catch (e: Exception) {
            val exceptionMessage = e.localizedMessage
            LogUtils.v("retrofit step 3: process EXCEPTION: $exceptionMessage")
            return eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse.Error(eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError.Exception(exceptionMessage))
        }
    }

    private fun isNetworkConnected(context: Context): Boolean {
        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
