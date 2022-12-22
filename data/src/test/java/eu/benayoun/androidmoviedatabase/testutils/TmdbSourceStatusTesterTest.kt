package eu.benayoun.androidmoviedatabase.testutils

import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import org.junit.Test


class TmdbSourceStatusTesterTest {
    @Test
    fun test_All_Statuses_Equality(){
        var status : TmdbSourceStatus= TmdbSourceStatus.None
        TmdbSourceStatusTester.assertEquality(status,status)

        status = TmdbSourceStatus.Internet
        TmdbSourceStatusTester.assertEquality(status,status)

        var error : TmdbAPIError = TmdbAPIError.NoInternet
        status = TmdbSourceStatus.Cache(error)
        TmdbSourceStatusTester.assertEquality(status,status)

        error = TmdbAPIError.ToolError
        status = TmdbSourceStatus.Cache(error)
        TmdbSourceStatusTester.assertEquality(status,status)

        error = TmdbAPIError.NoData
        status = TmdbSourceStatus.Cache(error)
        TmdbSourceStatusTester.assertEquality(status,status)

        error = TmdbAPIError.Exception("exception!")
        status = TmdbSourceStatus.Cache(error)
        TmdbSourceStatusTester.assertEquality(status,status)

        error = TmdbAPIError.Unknown
        status = TmdbSourceStatus.Cache(error)
        TmdbSourceStatusTester.assertEquality(status,status)

        status = TmdbSourceStatus.SerializationProblem
        TmdbSourceStatusTester.assertEquality(status,status)
    }

    // You do not see a test_All_Statuses_INEQUALITY test that would secure even more TmdbSourceStatusTester.
    // It's because:
    // * It would imply to change the method in TmdbSourceStatusTester to return a boolean.
    // * It would test a thing that we doesn't need NOW: inequality of statuses

}