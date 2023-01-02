package eu.benayoun.androidmoviedatabase.data.source.network

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import kotlinx.coroutines.delay

// parameter beforeGettingPopularMovies is used in fake flavor
// to modify behavior on each call of getPopularMovies

internal class FakeTmdbDataSource(val beforeGettingPopularMovies: () -> Unit={}): TmdbDataSource {
    private var delayInMs : Long = 0
    private var nextResponseIsSuccess = true
    private var tmdbAPIError = TmdbAPIError.Unknown as TmdbAPIError
    private val defaultList = FakeTmdbMovieListGenerator.getDefaultList()


    // interface implementation
    override suspend fun getPopularMovies(): TmdbAPIResponse {
        beforeGettingPopularMovies()
        delay(delayInMs)
        return if (nextResponseIsSuccess) getSuccessResponse()
        else getErrorResponse()
    }

    //***
    // set fake data
    //***
    fun setDelayInMs(newDelayInMs: Long){
        delayInMs = newDelayInMs
    }

    fun setSuccessResponse(){
        nextResponseIsSuccess=true
    }

    // errors

    fun setNoInternetErrorResponse(){
        setErrorResponse(TmdbAPIError.NoInternet)
    }

    fun setToolErrorResponse(){
        setErrorResponse(TmdbAPIError.ToolError)
    }

    fun setNoDataErrorResponse(){
        setErrorResponse(TmdbAPIError.NoData)
    }

    fun setExceptionErrorResponse(localizedMessage: String){
        setErrorResponse(TmdbAPIError.Exception(localizedMessage))
    }

    // F.Y.I there is no setUnknownErrorResponse()
    // because this value is not generated when you fetch data from the web but when you can't have data from data source

    fun setErrorResponse(tmdbAPIError: TmdbAPIError){
        nextResponseIsSuccess=false
        this.tmdbAPIError = tmdbAPIError
    }

    // ***
    // private cooking
    // ***

    private suspend fun getSuccessResponse() : TmdbAPIResponse {
        return TmdbAPIResponse.Success(defaultList)
    }

    private suspend fun getErrorResponse() : TmdbAPIResponse {
        return TmdbAPIResponse.Error(tmdbAPIError)
    }
}