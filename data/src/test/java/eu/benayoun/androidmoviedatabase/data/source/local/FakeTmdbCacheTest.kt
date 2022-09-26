package eu.benayoun.androidmoviedatabase.data.source.local

import com.google.common.truth.Truth
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FakeTmdbCacheTest {
    val fakeTmdbCache = FakeTmdbCache()

    @Before
    fun setUp() {
        // nothing to do
    }

    @After
    fun tearDown() {
        // nothing to do
    }

    @Test
    fun saveTmdbMovieList() = runTest(UnconfinedTestDispatcher()){
        // Arrange
        val expectedList = FakeTmdbMovieListGenerator.getDefaultList()
        var testList : List<TmdbMovie> = listOf()

        // Act
        fakeTmdbCache.saveTmdbMovieList(expectedList)
        fakeTmdbCache.getTmdbMovieListFlow().take(1).collect{
            testList=it
        }

        // Assert
        Truth.assertThat(testList.size).isEqualTo(expectedList.size)
    }

    @Test
    fun saveTmdbMetaData() {
    }
}