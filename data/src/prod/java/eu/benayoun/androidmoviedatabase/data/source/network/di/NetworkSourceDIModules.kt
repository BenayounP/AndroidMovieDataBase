package eu.benayoun.androidmoviedatabase.data.source.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import eu.benayoun.androidmoviedatabase.data.source.network.retrofit.RetrofitTmdbDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TmdbDataSourceProvider


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @TmdbDataSourceProvider
    @Singleton
    @Provides
    internal fun providesRetrofitTmdbDataSource(@ApplicationContext context: Context) : TmdbDataSource {
        return RetrofitTmdbDataSource(context)
    }
}