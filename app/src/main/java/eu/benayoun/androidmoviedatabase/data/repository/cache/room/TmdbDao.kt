package eu.benayoun.androidmoviedatabase.data.repository.cache.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.benayoun.androidmoviedatabase.data.repository.cache.TmdbCache

@Dao
interface TmdbDao{
    @Query("SELECT * FROM tmdbDataBase order by releaseDate DESC")
    fun getAll(): List<TmdbMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<TmdbMovieEntity>)
}