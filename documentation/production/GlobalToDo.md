# Global ToDo

## Product
* Specific use case to handle: The user use the app for first time with no connection. The app should display something like: "It's embarrassing. It's the firts launch and we can't fetch any data. Try later" 

## Architecture/Meta
* Add [Fastlane](https://docs.fastlane.tools/getting-started/android/setup/) for CI?

## Code cleanness
* Change the "compose" package name?
* Compare code with [now in android](https://github.com/android/nowinandroid)
* Replace the ugly companion object used for dimensions on compose with...what? (that's the problem)

## Data
* Paging Data from TMDB API?

## UI
* use the [experimental pullToRefresh](https://developer.android.com/reference/kotlin/androidx/compose/material/pullrefresh/package-summary#(androidx.compose.ui.Modifier).pullRefresh(androidx.compose.material.pullrefresh.PullRefreshState,kotlin.Boolean)) native function? 
* Add a config screen to change theme (light or dark)?

## Tests
* Clean the test part of `build.gradle`
* Test Room DB and data store (with robolectric!)
* Complete test part in HowTo.md








 