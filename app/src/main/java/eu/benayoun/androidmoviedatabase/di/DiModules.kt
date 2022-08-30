package eu.benayoun.androidmoviedatabase.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.repository.DefaultTmdbRepository
import eu.benayoun.androidmoviedatabase.data.repository.TmdbRepository
import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.room.RoomTmdbCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.repository.cache.room.TmdbDataBase
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
annotation class RoomTmdbCacheProvider

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @RoomTmdbCacheProvider
    @Singleton
    @Provides
    fun providesRoomTmdbCache(@TmdbDaoProvider tmdbDao: TmdbDao) :TmdbCache
    {
        return RoomTmdbCache(tmdbDao)
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