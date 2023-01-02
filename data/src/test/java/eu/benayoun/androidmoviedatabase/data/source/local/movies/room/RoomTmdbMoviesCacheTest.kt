package eu.benayoun.androidmoviedatabase.data.source.local.movies.room

import androidx.room.Room
import com.google.common.truth.Truth
import eu.benayoun.androidmoviedatabase.AndroidLocalTest
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal.TmdbRoomDao
import eu.benayoun.androidmoviedatabase.data.source.local.movies.room.internal.TmdbRoomDataBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
class RoomTmdbMoviesCacheTest: AndroidLocalTest(){
    private lateinit var tmdbRoomDao: TmdbRoomDao
    private lateinit var tmdbRoomDatabase : TmdbRoomDataBase
    private lateinit var roomTmdbMoviesCache: RoomTmdbMoviesCache

    @Before
    fun setup() {
        tmdbRoomDatabase = Room.inMemoryDatabaseBuilder(applicationContext,TmdbRoomDataBase::class.java).allowMainThreadQueries().build()
        tmdbRoomDao = tmdbRoomDatabase.tmdbDao()
        roomTmdbMoviesCache = RoomTmdbMoviesCache(tmdbRoomDao)
    }

    @After
    fun cleanup() {
        tmdbRoomDatabase.close()
    }

    @Test
    fun testInstantiations() = runTest {
        // ARRANGE
        // nothing to do

        // ACT
        // nothing to do

        //ASSERT
        Truth.assertWithMessage("tmdbRoomDatabase instantiation: ").that(tmdbRoomDatabase).isNotNull()
        Truth.assertWithMessage("tmdbRoomDao instantiation: ").that(tmdbRoomDao).isNotNull()
        Truth.assertWithMessage("roomTmdbMoviesCache instantiation: ").that(roomTmdbMoviesCache).isNotNull()
    }

    @Test
    fun testListInDBAfterInsertions() = runTest {
        // ARRANGE
        val insertedList = FakeTmdbMovieListGenerator.getDefaultList()
        val expectedDBSize= insertedList.size

        // ACT
        roomTmdbMoviesCache.saveTmdbMovieList(insertedList)

        //ASSERT
        val actualList = roomTmdbMoviesCache.getTmdbMovieListFlow().first()
        // size first
        val actualDBSize =actualList.size
        Truth.assertWithMessage("userActivityDatabase size: ").that(actualDBSize).isEqualTo(expectedDBSize)

        // content after
        actualList.forEachIndexed{index, actualTmdbMovie ->
            Truth.assertThat(actualTmdbMovie).isEqualTo(insertedList[index])
        }
    }






}