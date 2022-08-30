package eu.benayoun.androidmoviedatabase.data.repository.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.benayoun.androidmoviedatabase.data.repository.cache.room.TmdbMovieEntity
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie


interface TmdbCache {
    suspend fun getTmdbMovieList(): List<TmdbMovie>
    suspend fun saveTmdbMovieList(movieList: List<TmdbMovie>)
}