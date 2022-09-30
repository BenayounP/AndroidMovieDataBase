package eu.benayoun.androidmoviedatabase.data.source.network

import eu.benayoun.androidmoviedatabase.utils.LogUtils

internal class FakeDataSourceManager() {
    private var fakeResultIsSuccess: Boolean = true
    val fakeTmdbDataSource = FakeTmdbDataSource(::beforeGettingPopularMovies)
    init{
        fakeTmdbDataSource.setDelayInMs(1000)
    }

    private fun beforeGettingPopularMovies(){
        when (fakeResultIsSuccess){
            true-> fakeTmdbDataSource.setSuccessResponse()
            false -> fakeTmdbDataSource.setExceptionErrorResponse("FUCK")
        }
        fakeResultIsSuccess=!fakeResultIsSuccess
    }


}