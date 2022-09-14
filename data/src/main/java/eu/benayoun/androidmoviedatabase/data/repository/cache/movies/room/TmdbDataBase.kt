package eu.benayoun.androidmoviedatabase.data.repository.cache.movies.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TmdbMovieEntity::class], version = 1)
abstract class TmdbDataBase : RoomDatabase() {
    companion object {
        const val DB_NAME = "tmdbDataBase"

        fun create(context: Context): TmdbDataBase {
            return Room.databaseBuilder(
                context,
                TmdbDataBase::class.java,
                DB_NAME
            ).build()
        }
    }

    abstract fun tmdbDao() : TmdbDao
}