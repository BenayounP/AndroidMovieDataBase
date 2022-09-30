package eu.benayoun.androidmoviedatabase.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.di.TmdbDaoProvider
import eu.benayoun.androidmoviedatabase.data.source.local.RoomDataStoreTmdbCache
import eu.benayoun.androidmoviedatabase.data.source.local.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.datastore.DataStoreTmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.di.TmdbDataSourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TmdbRepositoryProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultTmdbRepositoryWithFakeDataSourceProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DataStoreTmdbMetaDataCacheProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RoomTmdbCacheProvider

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @DataStoreTmdbMetaDataCacheProvider
    @Singleton
    @Provides
    internal fun providesDataStoreTmdbMetaDataCache(@ApplicationContext appContext: Context) : TmdbMetaDataCache
    {
        return DataStoreTmdbMetaDataCache(appContext)
    }

    @RoomTmdbCacheProvider
    @Singleton
    @Provides
    internal fun providesRoomDataStoreTmdbCache(@TmdbDaoProvider tmdbDao: TmdbDao, @DataStoreTmdbMetaDataCacheProvider tmdbMetaDataCache : TmdbMetaDataCache) : TmdbCache
    {
        return RoomDataStoreTmdbCache(tmdbDao, tmdbMetaDataCache)
    }

    @TmdbRepositoryProvider
    @Singleton
    @Provides
    internal fun providestRetrofitTMDBRepositoryProvider(
        @TmdbDataSourceProvider TMDBDataSource: TmdbDataSource,
        @RoomTmdbCacheProvider tmdbCache: TmdbCache
    ): TmdbRepository {
        return eu.benayoun.androidmoviedatabase.data.repository.DefaultTmdbRepository(
            TMDBDataSource,
            tmdbCache,
            externalScope = MainScope(),
            dispatcher = Dispatchers.IO
        )
    }
}