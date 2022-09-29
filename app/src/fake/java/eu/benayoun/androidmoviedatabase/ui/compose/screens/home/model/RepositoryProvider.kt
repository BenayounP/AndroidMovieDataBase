package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.model

import eu.benayoun.androidmoviedatabase.data.di.DefaultTmdbRepositoryWithFakeDataSourceProvider
import eu.benayoun.androidmoviedatabase.data.di.FakeTmdbDataSourceProvider
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import javax.inject.Inject

class RepositoryProvider @Inject constructor (@DefaultTmdbRepositoryWithFakeDataSourceProvider private val tmdbRepository: TmdbRepository,
                                              @FakeTmdbDataSourceProvider private val fakeTmdbDataSource: FakeTmdbDataSource) {

    var fakeResultIsSuccess: Boolean = true

    fun providesRepository() : TmdbRepository = tmdbRepository

    fun onInit(){
        LogUtils.v("FAKE")
        fakeTmdbDataSource.setDelayinMs(1000)
    }

     fun onUpdateTmdbMovies(){
        when (fakeResultIsSuccess){
            true-> fakeTmdbDataSource.setSuccessResponse()
            false -> fakeTmdbDataSource.setExceptionErrorResponse("FUCK")
        }
        fakeResultIsSuccess=!fakeResultIsSuccess
    }
}