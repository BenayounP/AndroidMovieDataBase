package eu.benayoun.androidmoviedatabase.data.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TmdbMovieEntity::class], version = 1)
abstract class TmdbDataBase : RoomDatabase() {
    companion object {
        const val DB_NAME = "tmdbDataBase"
    }

    abstract fun tmdbDao() : TmdbDao
}