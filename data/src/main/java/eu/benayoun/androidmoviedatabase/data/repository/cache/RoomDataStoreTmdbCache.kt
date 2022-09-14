package eu.benayoun.androidmoviedatabase.data.repository.cache

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room.TmdbMovieEntity
import kotlinx.coroutines.flow.Flow

class RoomDataStoreTmdbCache(private val tmdbDao: TmdbDao, private val tmdbMetaDataCache: TmdbMetaDataCache) :
    TmdbCache {
    override suspend fun getTmdbMovieList(): List<TmdbMovie> {
        return tmdbDao.getAll().map{
            it.toTmdbMovie()
        }
    }

    override suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) {
        tmdbDao.insertAll(movieList.map{ TmdbMovieEntity(it) })
    }

    override fun getTmdbMetaDataFlow(): Flow<eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata> = tmdbMetaDataCache.getTmdbMetaDataFlow()

    override suspend fun saveTmdbMetaData(tmdbMetadata: eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata) = tmdbMetaDataCache.saveTmdbMetaData(tmdbMetadata)
}