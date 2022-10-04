package eu.benayoun.androidmoviedatabase.data.source.local.movies.room

import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.source.local.movies.TmdbMoviesCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal.TmdbRoomDao
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal.TmdbRoomMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomTmdbMoviesCache(private val tmdbRoomDao: TmdbRoomDao) : TmdbMoviesCache {
    override suspend fun getTmdbMovieListFlow(): Flow<List<TmdbMovie>> {
        return tmdbRoomDao.getAllMovies().map{
            it.map(TmdbRoomMovieEntity::asTmdbMovie)
        }
    }

    override suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>) {
        tmdbRoomDao.insertAllMovies(movieList.map{ TmdbRoomMovieEntity(it) })
    }
}