package eu.benayoun.androidmoviedatabase.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.repository.RetrofitTMDBRepository
import eu.benayoun.androidmoviedatabase.data.repository.TMDBRepository
import eu.benayoun.androidmoviedatabase.data.source.TMDBDataSource
import eu.benayoun.androidmoviedatabase.data.source.retrofit.RetrofitTMDBDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

// COROUTINE SCOPES
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScopeProvider

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {
    @Singleton
    @ApplicationScopeProvider
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}


// DATA SOURCE
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitTMDBDataSourceProvider

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @RetrofitTMDBDataSourceProvider
    @Singleton
    @Provides
    fun providesTMDBDataSource() : TMDBDataSource {
        return RetrofitTMDBDataSource()
    }
}

// REPOSITORY

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitTMDBRepositoryProvider

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @RetrofitTMDBRepositoryProvider
    @Singleton
    @Provides
    fun providesRetrofitTMDBRepositoryProvider(
        @RetrofitTMDBDataSourceProvider retrofitTMDBDataSource: RetrofitTMDBDataSource,
        @ApplicationScopeProvider externalScope: CoroutineScope
    ): TMDBRepository {
        return RetrofitTMDBRepository(retrofitTMDBDataSource, externalScope)
    }
}