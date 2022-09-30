package eu.benayoun.androidmoviedatabase.data.source.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDataBase
import javax.inject.Qualifier
import javax.inject.Singleton



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