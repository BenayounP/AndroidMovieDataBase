package eu.benayoun.androidmoviedatabase.data.source.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.benayoun.androidmoviedatabase.data.source.local.TmdbCache
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.datastore.DataStoreTmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.TmdbMoviesCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.RoomTmdbMoviesCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal.TmdbRoomDao
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal.TmdbRoomDataBase
import javax.inject.Qualifier
import javax.inject.Singleton



@Retention(AnnotationRetention.RUNTIME)
@Qualifier
internal annotation class TmdbDataStoreRoomMoviesCacheProvider

@InstallIn(SingletonComponent::class)
@Module
internal class LocalSourcesDIModules{

    // METADATA
    @Singleton
    @Provides
    internal fun providesDataStoreTmdbMetaDataCache(@ApplicationContext appContext: Context) : TmdbMetaDataCache
    {
        return DataStoreTmdbMetaDataCache(appContext)
    }

    // MOVIES

    @Provides
    @Singleton
    internal fun provideRoomDatabase(@ApplicationContext context: Context): TmdbRoomDataBase =
        TmdbRoomDataBase.create(context)

    @Singleton
    @Provides
    internal fun providesRoomTmdbDao(tmdbRoomDataBase: TmdbRoomDataBase): TmdbRoomDao {
        return tmdbRoomDataBase.tmdbDao()
    }

    @Singleton
    @Provides
    internal fun providesTmdbRoomMoviesCache(tmdbRoomDao: TmdbRoomDao): TmdbMoviesCache {
        return RoomTmdbMoviesCache(tmdbRoomDao)
    }

    // CACHE
    @Singleton
    @Provides
    @TmdbDataStoreRoomMoviesCacheProvider
    internal fun providesTmdbDataStoreRoomMoviesCache(tmdbMoviesCache: TmdbMoviesCache, tmdbMetaDataCache: TmdbMetaDataCache): TmdbCache {
        return TmdbCache(tmdbMoviesCache,tmdbMetaDataCache)
    }
}