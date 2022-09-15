package eu.benayoun.androidmoviedatabase.data.source.local.movies.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TmdbDao{
    @Query("SELECT * FROM tmdbDataBase order by releaseDate DESC")
    fun getAllMovies(): Flow<List<TmdbMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<TmdbMovieEntity>)
}