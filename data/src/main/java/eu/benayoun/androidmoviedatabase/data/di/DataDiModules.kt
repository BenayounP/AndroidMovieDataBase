package eu.benayoun.androidmoviedatabase.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.source.local.RoomDataStoreTmdbCache
import eu.benayoun.androidmoviedatabase.data.source.local.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.datastore.DataStoreTmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDataBase
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.retrofit.RetrofitTmdbDataSource
import kotlinx.coroutines.MainScope
import javax.inject.Qualifier
import javax.inject.Singleton


// DATA SOURCE
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitTmdbDataSourceProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FakeTmdbDataSourceProvider

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @RetrofitTmdbDataSourceProvider
    @Singleton
    @Provides
    internal fun providesRetrofitTmdbDataSource(@ApplicationContext context: Context) : TmdbDataSource {
        return RetrofitTmdbDataSource(context)
    }

    @FakeTmdbDataSourceProvider
    @Singleton
    @Provides
    internal fun providesFakeTmdbDataSource() : TmdbDataSource {
        return FakeTmdbDataSource()
    }
}

// CACHE

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class TmdbDaoProvider

@InstallIn(SingletonComponent::class)
@Module
class RoomModule{

    @Provides
    @Singleton
    internal fun provideDatabase(@ApplicationContext context: Context): TmdbDataBase =
        TmdbDataBase.create(context)

    @Singleton
    @TmdbDaoProvider
    @Provides
    internal fun providesTmdbDao(tmdbDataBase: TmdbDataBase): TmdbDao {
        return tmdbDataBase.tmdbDao()
    }
}

// REPOSITORY

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultTmdbRepositoryProvider

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultTmdbRepositoryWithFalseDataSourceProvider

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

    @DefaultTmdbRepositoryProvider
    @Singleton
    @Provides
    internal fun providesdefaultRetrofitTMDBRepositoryProvider(
        @RetrofitTmdbDataSourceProvider TMDBDataSource: TmdbDataSource,
        @RoomTmdbCacheProvider tmdbCache: TmdbCache
    ): TmdbRepository {
        return eu.benayoun.androidmoviedatabase.data.repository.DefaultTmdbRepository(
            TMDBDataSource,
            tmdbCache,
            externalScope = MainScope()
        )
    }

    @DefaultTmdbRepositoryWithFalseDataSourceProvider
    @Singleton
    @Provides
    internal fun providesdefaultRetrofitTMDBRepositoryWithFalseDataSourceProviderProvider(
        @FakeTmdbDataSourceProvider TMDBDataSource: TmdbDataSource,
        @RoomTmdbCacheProvider tmdbCache: TmdbCache
    ): TmdbRepository {
        return eu.benayoun.androidmoviedatabase.data.repository.DefaultTmdbRepository(
            TMDBDataSource,
            tmdbCache,
            externalScope = MainScope()
        )
    }
}