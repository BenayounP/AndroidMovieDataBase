package eu.benayoun.androidmoviedatabase.data.repository.cache.room

import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie

class RoomTmdbCache(private val tmdbDao: TmdbDao) : TmdbCache {
    override suspend fun getTmdbMovies(): List<TmdbMovie> {
        return tmdbDao.getAll().map{
            it.toTmdbMovie()
        }
    }

    override suspend fun saveTmdbMovies(movies: List<TmdbMovie>) {
        tmdbDao.insertAll(movies.map{TmdbMovieEntity(it)})
    }

}