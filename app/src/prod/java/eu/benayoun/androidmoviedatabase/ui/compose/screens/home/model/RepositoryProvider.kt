package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.model

import eu.benayoun.androidmoviedatabase.data.di.DefaultTmdbRepositoryProvider
import eu.benayoun.androidmoviedatabase.data.di.DefaultTmdbRepositoryWithFakeDataSourceProvider
import eu.benayoun.androidmoviedatabase.data.di.FakeTmdbDataSourceProvider
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import javax.inject.Inject

class RepositoryProvider @Inject constructor (@DefaultTmdbRepositoryProvider private val tmdbRepository: TmdbRepository) {
    fun providesRepository() : TmdbRepository = tmdbRepository

    fun onInit(){
        LogUtils.v("Prod")
    }

     fun onUpdateTmdbMovies(){
       // nothing to do
    }
}