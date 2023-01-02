package eu.benayoun.androidmoviedatabase.data.source.local

import com.google.common.truth.Truth
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.source.local.metadata.FakeTmdbMetaDataCache
import eu.benayoun.androidmoviedatabase.data.source.local.movies.FakeTmdbMoviesCache
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FakeTmdbCacheTest {
    private val fakeTmdbCache = TmdbCache(FakeTmdbMoviesCache(), FakeTmdbMetaDataCache())

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
        for (index in testList.indices){
            Truth.assertThat(testList[index]).isEqualTo(expectedList[index])
        }
    }

    @Test
    fun saveTmdbMetaData()  = runTest(UnconfinedTestDispatcher()){
        // Arrange
        val expectedLastInternetSuccessTimeStamp = 4200L
        var expectedTmdbMetaData= TmdbMetadata(TmdbSourceStatus.None,expectedLastInternetSuccessTimeStamp)
        var testTmdbMetaData = TmdbMetadata()

        // Act
        fakeTmdbCache.saveTmdbMetaData(expectedTmdbMetaData)
        fakeTmdbCache.getTmdbMetaDataFlow().take(1).collect{
            testTmdbMetaData=it
        }

        // Assert
        Truth.assertThat(testTmdbMetaData).isEqualTo(expectedTmdbMetaData)
    }
}