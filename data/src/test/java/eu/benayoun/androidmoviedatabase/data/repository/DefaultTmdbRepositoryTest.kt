package eu.benayoun.androidmoviedatabase.data.repository

import com.google.common.truth.Truth
import eu.benayoun.androidmoviedatabase.data.model.TmdbMovie
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.source.local.FakeTmdbCache
import eu.benayoun.androidmoviedatabase.data.source.network.FakeTmdbDataSource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Test

class DefaultTmdbRepositoryTest {
    private val fakeTmdbDataSource = FakeTmdbDataSource()
    private val fakeTmdbCache = FakeTmdbCache()
    private val dispatcher = UnconfinedTestDispatcher()
    internal val defaultTmdbRepository = DefaultTmdbRepository(fakeTmdbDataSource,fakeTmdbCache,MainScope(), dispatcher)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun updateTmdbMovies_SourceStatus_SUCCESS() = runTest(UnconfinedTestDispatcher()){
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
    fun updateTmdbMovies_SourceStatus_Error_noInternet() = runTest(UnconfinedTestDispatcher()){
        updateTmdbMovies_SourceStatus_ERROR(TmdbAPIError.NoInternet())
    }

    @Test
    fun updateTmdbMovies_SourceStatus_Error_ToolError() = runTest(UnconfinedTestDispatcher()){
        updateTmdbMovies_SourceStatus_ERROR(TmdbAPIError.ToolError())
    }

    @Test
    fun updateTmdbMovies_SourceStatus_Error_NoData() = runTest(UnconfinedTestDispatcher()){
        updateTmdbMovies_SourceStatus_ERROR(TmdbAPIError.NoData())
    }

    @Test
    fun updateTmdbMovies_SourceStatus_Error_Exception() = runTest(UnconfinedTestDispatcher()){
        updateTmdbMovies_SourceStatus_ERROR(TmdbAPIError.Exception("an exception occurred"))
    }

    private suspend  fun updateTmdbMovies_SourceStatus_ERROR(expectedAPIError : TmdbAPIError) {
        // Arrange
        val expectedStatus = TmdbSourceStatus.Cache(expectedAPIError)
        var testedStatus : TmdbSourceStatus = TmdbSourceStatus.None()

        fakeTmdbDataSource.setErrorResponse(expectedAPIError)

        // Act
        defaultTmdbRepository.updateTmdbMovies()
        defaultTmdbRepository.getTmdbMetaDataFlow().take(1).collect{
            testedStatus = it.tmdbSourceStatus
        }

        // Assert
        Truth.assertThat(testedStatus).isInstanceOf(expectedStatus::class.java)
        if (testedStatus is TmdbSourceStatus.Cache){
            val testedAPIError = (testedStatus as TmdbSourceStatus.Cache).tmdbAPIError
            Truth.assertThat(testedAPIError).isInstanceOf(expectedAPIError::class.java)
            if (testedAPIError is TmdbAPIError.Exception){
                val testedLocalizedMessage = (testedAPIError as TmdbAPIError.Exception).localizedMessage
                Truth.assertThat(testedLocalizedMessage).isEqualTo((expectedAPIError as TmdbAPIError.Exception).localizedMessage)
            }
        }
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