package eu.benayoun.androidmoviedatabase.data.source.local

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.TmdbMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomDataStoreTmdbCache(private val tmdbDao: TmdbDao, private val tmdbMetaDataCache: TmdbMetaDataCache) :
    TmdbCache {
    override suspend fun getTmdbMovieListFlow(): Flow<List<TmdbMovie>> {
        return tmdbDao.getAllMovies().map{
            it.map(TmdbMovieEntity::asTmdbMovie)
        }
    }

    override suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) {
        tmdbDao.insertAllMovies(movieList.map{ TmdbMovieEntity(it) })
    }

    override fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbMetaDataCache.getTmdbMetaDataFlow()

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata) = tmdbMetaDataCache.saveTmdbMetaData(tmdbMetadata)
}