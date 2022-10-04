package eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TmdbRoomDao{
    @Query("SELECT * FROM tmdbDataBase order by releaseDate DESC")
    fun getAllMovies(): Flow<List<TmdbRoomMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<TmdbRoomMovieEntity>)
}