package eu.benayoun.androidmoviedatabase.data.source.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDataBase
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.retrofit.RetrofitTmdbDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

// NETWORK
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
    internal fun providesFakeTmdbDataSource() : FakeTmdbDataSource {
        return FakeTmdbDataSource()
    }
}

// LOCAL

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