# Bibliography

## Disclaimer
This is the "main" bibliography. You should also see references that helped me directly in the code. 

## Links

### Architecture
* How to create a multi gradle module app
  * [Personal blog from Nikola K.](https://cinnamon.agency/blog/post/multi_module_apps_with_kotlin_and_dagger)
* How to create an offline first app :
  * [Android developers post](https://developer.android.com/topic/architecture/data-layer/offline-first)

### Dependency Injection
* How to use @Injects, @Binds and @Provides with Hilt
  * [Medium post](https://www.valueof.io/blog/inject-provides-binds-dependencies-dagger-hilt)

### Data source
* How to use retrofit with Room and especially flow
  * [Medium post](https://narendrasinhdodiya.medium.com/android-architecture-mvvm-with-coroutines-retrofit-hilt-kotlin-flow-room-48e67ca3b2c8)
  * [Github repository](https://github.com/devnarendra08/DemoTMDB)
* An example of use of TMDB API that helped me to "secure" the api key
  * [Medium post](https://skydoves.medium.com/android-mvvm-architecture-components-using-the-movie-database-api-8fbab128d7)
  * [GitHub repository](https://github.com/skydoves/TheMovies)

### Local data
* **ALL** about Proto DataStore
  * [Medium post](https://medium.com/androiddevelopers/all-about-proto-datastore-1b1af6cd2879)

### UI
* How to handle flow and state in ViewModel and Composables
  * [Medium post](https://proandroiddev.com/better-handling-states-between-viewmodel-and-composable-7ca14af379cb)
* How to handle **Lifecycle** in ViewModel and Composables
  * [Medium post](https://betterprogramming.pub/jetpack-compose-with-lifecycle-aware-composables-7bd5d6793e0)

### Tests
#### Data layer tests
* Testing flows in Android
  * [Android developers post](https://developer.android.com/kotlin/flow/test)
* Test Dispatchers
  * [Android developers post](https://developer.android.com/kotlin/coroutines/test#testdispatchers)

#### UI tests
* Testing Compose layout
  * [Introduction Android developers post](https://developer.android.com/jetpack/compose/testing)
  * So useful [CheatSheet](https://developer.android.com/jetpack/compose/testing-cheatsheet)

#### Miscellaneous
* Posts that helped me to avoid crashs in local test while using `android.util.Log` 🤷 
  * [Medium post](https://medium.com/@gal_41749/android-unitests-and-log-class-9546b6480006) that resolves it with...one [class](../../data/src/test/java/android/util/Log.kt)!
  * [Stack overflow](https://stackoverflow.com/a/69634728/1859993) to add `@JvmStatic`
