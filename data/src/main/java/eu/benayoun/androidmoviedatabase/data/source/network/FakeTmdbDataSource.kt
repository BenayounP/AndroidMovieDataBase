package eu.benayoun.androidmoviedatabase.data.source.network

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIResponse
import kotlinx.coroutines.delay

class FakeTmdbDataSource: TmdbDataSource {
    private var delayInMs : Long = 0
    private var nextResponseIsSuccess = true
    private var tmdbAPIError = TmdbAPIError.Unknown() as TmdbAPIError
    private val defaultList = generateDefaultList()


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

    private fun generateDefaultList() : List<TmdbMovie> = listOf(
        TmdbMovie(id=532639, title="Pinocchio", posterUrl="https://image.tmdb.org/t/p/original/h32gl4a3QxQWNiNaR4Fc1uvLBkV.jpg", releaseDate="2022-09-07"),
        TmdbMovie(id=921360, title="Wire Room", posterUrl="https://image.tmdb.org/t/p/original/b9ykj4v8ykjRoGB7SpI1OuxblNU.jpg", releaseDate="2022-09-02"),
        TmdbMovie(id=773867, title="Seoul Vibe", posterUrl="https://image.tmdb.org/t/p/original/ffX0TL3uKerLXACkuZGWhAPMbAq.jpg", releaseDate="2022-08-26"),
        TmdbMovie(id=629176, title="Samaritan", posterUrl="https://image.tmdb.org/t/p/original/vwq5iboxYoaSpOmEQrhq9tHicq7.jpg", releaseDate="2022-08-25"),
        TmdbMovie(id=760741, title="Beast", posterUrl="https://image.tmdb.org/t/p/original/iRV0IB5xQeOymuGGUBarTecQVAl.jpg", releaseDate="2022-08-11"),
        TmdbMovie(id=985939, title="Fall", posterUrl="https://image.tmdb.org/t/p/original/9f5sIJEgvUpFv0ozfA6TurG4j22.jpg", releaseDate="2022-08-11"),
        TmdbMovie(id=766507, title="Prey", posterUrl="https://image.tmdb.org/t/p/original/ujr5pztc1oitbe7ViMUOilFaJ7s.jpg", releaseDate="2022-08-02"),
        TmdbMovie(id=760161, title="Orphan: First Kill", posterUrl="https://image.tmdb.org/t/p/original/wSqAXL1EHVJ3MOnJzMhUngc8gFs.jpg", releaseDate="2022-07-27")
    )

}