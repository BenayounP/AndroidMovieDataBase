package eu.benayoun.androidmoviedatabase.data.source.network

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import kotlinx.coroutines.delay

class FakeTmdbDataSource: TmdbDataSource {
    private var delayInMs : Long = 0
    private var nextResponseIsSuccess = true
    private var tmdbAPIError = TmdbAPIError.Unknown() as TmdbAPIError
    private val defaultList = FakeTmdbMovieListGenerator.getDefaultList()


    // interface implementation
    override suspend fun getPopularMovies(): TmdbAPIResponse {
        delay(delayInMs)
        return if (nextResponseIsSuccess) getSuccessResponse()
        else getErrorResponse()
    }

    //***
    // set fake data
    //***
    fun setDelayinMs(newDelayInMs: Long){
        delayInMs = newDelayInMs
    }

    fun setSuccessResponse(){
        nextResponseIsSuccess=true
    }

    // errors

    fun setNoInternetErrorResponse(){
        nextResponseIsSuccess=false
        tmdbAPIError = TmdbAPIError.NoInternet()
    }

    fun setToolErrorResponse(){
        nextResponseIsSuccess=false
        tmdbAPIError = TmdbAPIError.ToolError()
    }

    fun setNoDataErrorResponse(){
        nextResponseIsSuccess=false
        tmdbAPIError = TmdbAPIError.NoData()
    }

    fun setExceptionErrorResponse(localizedMessage: String){
        nextResponseIsSuccess=false
        tmdbAPIError = TmdbAPIError.Exception(localizedMessage)
    }

    fun setUnknownErrorResponse(){
        nextResponseIsSuccess=false
        tmdbAPIError = TmdbAPIError.Unknown()
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