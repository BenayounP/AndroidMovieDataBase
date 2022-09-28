package eu.benayoun.androidmoviedatabase.data.repository

import com.google.common.truth.Truth
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.source.local.FakeTmdbCache
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class DefaultTmdbRepositoryTest {

    val fakeTmdbDataSource = FakeTmdbDataSource()
    val fakeTmdbCache = FakeTmdbCache()
    val dispatcher = UnconfinedTestDispatcher()
    internal val defaultTmdbRepository = DefaultTmdbRepository(fakeTmdbDataSource,fakeTmdbCache,MainScope(), dispatcher)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun updateTmdbMovies_Metadata_SUCCESS() = runTest(UnconfinedTestDispatcher()){
        // Arrange
        val expectedStatus = TmdbSourceStatus.Internet()
        var testStatus : TmdbSourceStatus = TmdbSourceStatus.None()

        // Act
        defaultTmdbRepository.updateTmdbMovies()
        defaultTmdbRepository.getTmdbMetaDataFlow().take(1).collect{
            testStatus = it.tmdbSourceStatus
        }

        // Assert
        Truth.assertThat(testStatus).isInstanceOf(expectedStatus::class.java)

    }

    @Test
    fun updateTmdbMovies_popularMovies_SUCCESS() = runTest(UnconfinedTestDispatcher()){
        // Arrange
        val expectedList = FakeTmdbMovieListGenerator.getDefaultList()
        var testList : List<TmdbMovie> = listOf()


        // Act
        defaultTmdbRepository.updateTmdbMovies()
        defaultTmdbRepository.getPopularMovieListFlow().take(1).collect{
            testList=it
        }

        // Assert
        Truth.assertThat(testList.size).isEqualTo(expectedList.size)
        for (index in testList.indices){
            Truth.assertThat(testList[index]).isEqualTo(expectedList[index])
        }
    }
}