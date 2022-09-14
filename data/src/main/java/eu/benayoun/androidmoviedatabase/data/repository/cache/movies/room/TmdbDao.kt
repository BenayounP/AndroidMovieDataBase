package eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface TmdbDao{
    @Query("SELECT * FROM tmdbDataBase order by releaseDate DESC")
    fun getAll(): List<TmdbMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<TmdbMovieEntity>)
}