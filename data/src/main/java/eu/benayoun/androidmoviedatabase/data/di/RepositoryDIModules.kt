package eu.benayoun.androidmoviedatabase.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.repository.DefaultTmdbRepository
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.di.TmdbDataStoreRoomMoviesCacheProvider
import eu.benayoun.androidmoviedatabase.data.source.local.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.di.TmdbDataSourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TmdbRepositoryProvider

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @TmdbRepositoryProvider
    @Singleton
    @Provides
    internal fun providesRetrofitTMDBRepositoryProvider(
        @TmdbDataSourceProvider TMDBDataSource: TmdbDataSource,
        @TmdbDataStoreRoomMoviesCacheProvider tmdbCache: TmdbCache
    ): TmdbRepository = DefaultTmdbRepository(
        TMDBDataSource,
        tmdbCache,
        externalScope = MainScope(),
        dispatcher = Dispatchers.IO
    )
}