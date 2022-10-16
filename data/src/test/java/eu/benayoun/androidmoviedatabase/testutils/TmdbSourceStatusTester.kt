package eu.benayoun.androidmoviedatabase.testutils

import com.google.common.truth.Truth
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus

object TmdbSourceStatusTester {
    fun assertEquality(actualStatus: TmdbSourceStatus, expectedStatus : TmdbSourceStatus){
        Truth.assertThat(actualStatus).isInstanceOf(expectedStatus::class.java)
        if (actualStatus is TmdbSourceStatus.Cache){
            val actualAPIError = actualStatus.tmdbAPIError
            val expectedAPIError = (expectedStatus as TmdbSourceStatus.Cache).tmdbAPIError
            Truth.assertThat(actualAPIError).isInstanceOf(expectedAPIError::class.java)
            if (actualAPIError is TmdbAPIError.Exception){
                val testedLocalizedMessage = (actualAPIError as TmdbAPIError.Exception).localizedMessage
                Truth.assertThat(testedLocalizedMessage).isEqualTo(actualAPIError.localizedMessage)
            }
        }
    }
}