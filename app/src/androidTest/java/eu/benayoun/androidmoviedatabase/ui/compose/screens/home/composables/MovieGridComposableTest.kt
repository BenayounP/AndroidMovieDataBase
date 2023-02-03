package eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables


import android.content.res.Resources
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.google.common.truth.Truth.assertThat
import eu.benayoun.androidmoviedatabase.data.model.fake.FakeTmdbMovieListGenerator
import eu.benayoun.androidmoviedatabase.ui.UIUtils
import org.junit.Rule
import org.junit.Test


class MovieGridComposableTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_swiping_down_on_top_calls_On_Pull_To_Refresh() {
        // Arrange
        var onPullToRefreshWasCalled=false

        fun onPullToRefreshTester(){
            onPullToRefreshWasCalled=true
        }
        val semanticMatcher = getTestableMovieGridComposableSemanticMatcher(::onPullToRefreshTester)

        //Act

        composeTestRule
            .onNode(semanticMatcher)
            .performTouchInput { swipeDown() }

        // Assert
        assertThat(onPullToRefreshWasCalled).isTrue()
    }

    @Test
    fun test_swiping_up_NOT_calls_On_Pull_To_Refresh() {
        // Arrange
        var onPullToRefreshWasCalled=false

        fun onPullToRefreshTester(){
            onPullToRefreshWasCalled=true
        }
        val semanticMatcher = getTestableMovieGridComposableSemanticMatcher(::onPullToRefreshTester)

        //Act
        composeTestRule
            .onNode(semanticMatcher)
            .performTouchInput { swipeUp() }

        // Assert
        assertThat(onPullToRefreshWasCalled).isFalse()
    }

    @Test
    fun test_swiping_down_NOT_on_top_NOT_calls_On_Pull_To_Refresh() {
        // Arrange
        var onPullToRefreshWasCalled=false

        fun onPullToRefreshTester(){
            onPullToRefreshWasCalled=true
        }
        val semanticMatcher = getTestableMovieGridComposableSemanticMatcher(::onPullToRefreshTester)

        //Act

        // swipe a little up
        composeTestRule
            .onNode(semanticMatcher)
            .performTouchInput {
                swipeUp() }

        composeTestRule
            .onNode(semanticMatcher)
            .performTouchInput {
                swipeUp() }

        // and down
        composeTestRule
            .onNode(semanticMatcher)
            .performTouchInput { swipeDown() }

        // Assert
        assertThat(onPullToRefreshWasCalled).isFalse()
    }

    private fun getTestableMovieGridComposableSemanticMatcher(onPullToRefreshTester: () -> Unit): SemanticsMatcher {
        val fakeMovieList = FakeTmdbMovieListGenerator.getDefaultList()
        val movieGridComposableTestTag = "movieGridComposableTestTag"

        composeTestRule.setContent {
            MovieGridComposable(
                modifier = Modifier.testTag(movieGridComposableTestTag),
                viewWidth = UIUtils.getScreenWidth(Resources.getSystem()),
                fakeMovieList, onPullToRefreshTester
            )
        }
        return hasTestTag(movieGridComposableTestTag)
    }

}