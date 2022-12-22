package eu.benayoun.androidmoviedatabase.data.source.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.source.network.FakeDataSourceManager
import eu.benayoun.androidmoviedatabase.data.source.network.TmdbDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class TmdbDataSourceProvider

@Module
@InstallIn(SingletonComponent::class)
internal class DataSourceModule {
    @TmdbDataSourceProvider
    @Singleton
    @Provides
    internal fun providesTmdbDataSource() : TmdbDataSource {
        val fakeDataSourceManager=  FakeDataSourceManager()
        return fakeDataSourceManager.fakeTmdbDataSource
    }
}