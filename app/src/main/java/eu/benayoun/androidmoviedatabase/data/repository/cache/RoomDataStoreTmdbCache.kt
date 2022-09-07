package eu.benayoun.androidmoviedatabase.data.repository.cache

import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbOrigin
import eu.benayoun.androidmoviedatabase.data.repository.cache.metadata.TmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room.TmdbDao
import eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room.TmdbMovieEntity
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie
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

    override fun getTmdbMetaDataFlow(): Flow<TmdbMetadata> = tmdbMetaDataCache.getTmdbMetaDataFlow()

    override suspend fun saveTmdbMetaData(tmdbMetadata: TmdbMetadata) = tmdbMetaDataCache.saveTmdbMetaData(tmdbMetadata)
}