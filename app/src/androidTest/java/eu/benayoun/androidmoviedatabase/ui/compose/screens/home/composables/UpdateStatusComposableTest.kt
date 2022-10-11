package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import eu.benayoun.androidmoviedatabase.R
import eu.benayoun.androidmoviedatabase.data.model.api.TmdbAPIError
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbMetadata
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbSourceStatus
import eu.benayoun.androidmoviedatabase.data.model.meta.TmdbUpdateStatus
import eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.home.UpdateStatusComposable
import eu.benayoun.androidmoviedatabase.utils.LogUtils
import org.junit.Rule
import org.junit.Test

class UpdateStatusComposableTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_display_updateStatus_Updating(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Updating
        val tmdbMetadata = TmdbMetadata()

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.update_status_updating)
    }

    @Test
    fun test_display_metadata_None(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Off
        val tmdbMetadata = TmdbMetadata(tmdbSourceStatus = TmdbSourceStatus.None)

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.source_status_none)
    }

    // no test_display_metaData_Internet() because it's seems impossible
    // no way to test the ABSENCE of cache text
    // No way to test the color of the composable 🤷‍

    @Test
    fun test_display_metaData_Cache_No_Internet(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Off
        val tmdbMetadata = TmdbMetadata(tmdbSourceStatus = TmdbSourceStatus.Cache(TmdbAPIError.NoInternet))

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.source_status_no_internet)
    }

    @Test
    fun test_display_metaData_Cache_Tool_Error(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Off
        val tmdbMetadata = TmdbMetadata(tmdbSourceStatus = TmdbSourceStatus.Cache(TmdbAPIError.ToolError))

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.source_status_tool_error)
    }

    @Test
    fun test_display_metaData_Cache_No_Data(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Off
        val tmdbMetadata = TmdbMetadata(tmdbSourceStatus = TmdbSourceStatus.Cache(TmdbAPIError.NoData))

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.source_status_no_data)
    }

    @Test
    fun test_display_metaData_Cache_Exception(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Off
        val tmdbMetadata = TmdbMetadata(tmdbSourceStatus = TmdbSourceStatus.Cache(TmdbAPIError.Exception("")))

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.source_status_exception)
    }

    @Test
    fun test_display_metaData_Cache_Unknown(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Off
        val tmdbMetadata = TmdbMetadata(tmdbSourceStatus = TmdbSourceStatus.Cache(TmdbAPIError.Unknown))

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.source_status_unknown)
    }

    @Test
    fun test_display_metaData_Unknown(){
        val tmdbUpdateStatus = TmdbUpdateStatus.Off
        val tmdbMetadata = TmdbMetadata(tmdbSourceStatus = TmdbSourceStatus.Unknown)

        testTextPresence(tmdbUpdateStatus,tmdbMetadata,R.string.source_status_unknown)
    }

    private fun testTextPresence(tmdbUpdateStatus: TmdbUpdateStatus, tmdbMetadata: TmdbMetadata, expectedTextId:Int){
        val testTag = "UpdateStatusComposableTestTag"
        var expectedText :  String = ""

        composeTestRule.setContent {
            UpdateStatusComposable(modifier = Modifier, tmdbUpdateStatus =tmdbUpdateStatus, tmdbMetadata = tmdbMetadata, textTestTag = testTag)
            expectedText= stringResource(expectedTextId)
        }
        composeTestRule.onNode(hasTestTag(testTag)).assertTextContains(expectedText, substring = true)
    }
}