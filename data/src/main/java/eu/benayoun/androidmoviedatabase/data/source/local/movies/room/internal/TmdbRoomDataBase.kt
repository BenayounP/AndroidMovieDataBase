package eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TmdbRoomMovieEntity::class], version = 1)
internal abstract class TmdbRoomDataBase : RoomDatabase() {
    companion object {
        const val DB_NAME = "tmdbDataBase"

        fun create(context: Context): TmdbRoomDataBase {
            return Room.databaseBuilder(
                context,
                TmdbRoomDataBase::class.java,
                DB_NAME
            ).build()
        }
    }

    abstract fun tmdbDao() : TmdbRoomDao
}