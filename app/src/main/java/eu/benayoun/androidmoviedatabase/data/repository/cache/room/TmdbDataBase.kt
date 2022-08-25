package eu.benayoun.androidmoviedatabase.data.repository.cache.room

import android.content.Context
import androidx.room.*
import eu.pbenayoun.thatdmdbapp.repository.model.TmdbMovie



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