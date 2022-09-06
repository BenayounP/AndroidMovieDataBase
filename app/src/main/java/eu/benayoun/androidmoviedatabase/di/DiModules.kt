package eu.benayoun.androidmoviedatabase.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.repository.DefaultTmdbRepository
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.repository.cache.RoomDataStoreTmdbCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.datastore.DataStoreTmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room.TmdbDataBase
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
    fun providesTmdbDataSource(@ApplicationContext context: Context) : TmdbDataSource {
        return RetrofitTmdbDataSource(context)
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
    fun provideDatabase(@ApplicationContext context: Context): TmdbDataBase =
        TmdbDataBase.create(context)

    @Singleton
    @TmdbDaoProvider
    @Provides
    fun providesTmdbDao(tmdbDataBase: TmdbDataBase): TmdbDao {
        return tmdbDataBase.tmdbDao()
    }
}


// REPOSITORY

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultTmdbRepositoryProvider

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
    fun providesDataStoreTmdbMetaDataCache(@ApplicationContext appContext: Context) : TmdbMetaDataCache
    {
        return DataStoreTmdbMetaDataCache(appContext)
    }

    @RoomTmdbCacheProvider
    @Singleton
    @Provides
    fun providesRoomDataStoreTmdbCache(@TmdbDaoProvider tmdbDao: TmdbDao, @DataStoreTmdbMetaDataCacheProvider tmdbMetaDataCache : TmdbMetaDataCache) : TmdbCache
    {
        return RoomDataStoreTmdbCache(tmdbDao, tmdbMetaDataCache)
    }

    @DefaultTmdbRepositoryProvider
    @Singleton
    @Provides
    fun providesRetrofitTMDBRepositoryProvider(
        @RetrofitTmdbDataSourceProvider TMDBDataSource: TmdbDataSource,
        @RoomTmdbCacheProvider tmdbCache: TmdbCache
    ): TmdbRepository {
        return DefaultTmdbRepository(TMDBDataSource,tmdbCache)
    }
}