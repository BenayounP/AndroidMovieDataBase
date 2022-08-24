package eu.benayoun.androidmoviedatabase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.repository.RetrofitTmdbRepository
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.TmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.retrofit.RetrofitTmdbDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

// DATA SOURCE
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitTmdbDataSourceProvider

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @RetrofitTmdbDataSourceProvider
    @Singleton
    @Provides
    fun providesTmdbDataSource() : TmdbDataSource {
        return RetrofitTmdbDataSource()
    }
}

// REPOSITORY

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitTmdbRepositoryProvider

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @RetrofitTmdbRepositoryProvider
    @Singleton
    @Provides
    fun providesRetrofitTMDBRepositoryProvider(
        @RetrofitTmdbDataSourceProvider TMDBDataSource: TmdbDataSource
    ): TmdbRepository {
        return RetrofitTmdbRepository(TMDBDataSource)
    }
}