package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables


import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class MovieGridComposableKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_swiping_down_calls_On_Pull_To_Refresh() {
        // Arrange
        val fakeMovieList = FakeTmdbMovieListGenerator.getDefaultList()
        var onPullToRefreshWasCalled=false
        val movieGridComposableTestTag = "movieGridComposableTestTag"

        fun onPullToRefreshTester(){
            onPullToRefreshWasCalled=true
        }

        //ACT

        composeTestRule.setContent {
            MovieGridComposable(modifier = Modifier.testTag(movieGridComposableTestTag),fakeMovieList,::onPullToRefreshTester)
        }

        composeTestRule
            .onNode(hasTestTag(movieGridComposableTestTag))
            .performTouchInput { swipeDown() }

        // Assert
        assertThat(onPullToRefreshWasCalled).isTrue()
    }

    @Test
    fun test_swiping_up_NOT_calls_On_Pull_To_Refresh() {
        // Arrange
        val fakeMovieList = FakeTmdbMovieListGenerator.getDefaultList()
        var onPullToRefreshWasCalled=false
        val movieGridComposableTestTag = "movieGridComposableTestTag"

        fun onPullToRefreshTester(){
            onPullToRefreshWasCalled=true
        }

        //ACT

        composeTestRule.setContent {
            MovieGridComposable(modifier = Modifier.testTag(movieGridComposableTestTag),fakeMovieList,::onPullToRefreshTester)
        }

        composeTestRule
            .onNode(hasTestTag(movieGridComposableTestTag))
            .performTouchInput { swipeUp() }

        // Assert
        assertThat(onPullToRefreshWasCalled).isFalse()
    }

}