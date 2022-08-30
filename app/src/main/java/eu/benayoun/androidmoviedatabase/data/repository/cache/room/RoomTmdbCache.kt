package eu.benayoun.androidmoviedatabase.data.repository.cache.room

import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

class RoomTmdbCache(private val tmdbDao: TmdbDao) : TmdbCache {
    override suspend fun getTmdbMovieList(): List<TmdbMovie> {
        return tmdbDao.getAll().map{
            it.toTmdbMovie()
        }
    }

    override suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) {
        tmdbDao.insertAll(movieList.map{TmdbMovieEntity(it)})
    }

}