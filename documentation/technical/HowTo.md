# How to

## Build Variants
There is two flavors:
* `fakeServer`: That works with a **fake** server with static data that you can
  find [here](../../data/src/main/java/eu/benayoun/androidmoviedatabase/data/model/fake/FakeTmdbMovieListGenerator.kt)
  . It gives you once in two refresh a **fake** server error
* `tmdbServer`: That works with the **real** TMDB API server. You need a TMDB API key to use it.

## Get real data from TMDB server
By default this app works with fake data from fake server.

### Step 1: Get a TMDB API key
1. Create an account [here](https://www.themoviedb.org/signup) (or [login](https://www.themoviedb.org/login)) on TMDB site
2. Follow these steps to get your API key [here](https://developers.themoviedb.org/3/getting-started/introduction)

### Step 2: Include API key in the project

Add your API key in `local.properties` file.

```
tmdb_api_key="[YOUR_API_KEY]"
```

Et voilà !

## Tests

### What

There is two set of tests:

* local/unit tests for data layer
  are [here](../../data/src/test/java/eu/benayoun/androidmoviedatabase/).
* Instrumented tests with compose tests for UI layer
  are [here](../../app/src/androidTest/java/eu/benayoun/androidmoviedatabase/ui/compose/screens/home/composables/)

### Launch all tests

I created a little script that allow you to launch all local and instrumented tests in debug variant
with a fake server.

You can launch it

* on Android Studio on Gradle Panel: AndrodiMovieDataBase > Tasks > verification >
  testLocalAndInstrumented
* Via your terminal: `./gradlew testLocalAndInstrumented`

On Android Studio, results are available here:

* Local tests [summary](../../data/build/reports/tests/testFakeserverDebugUnitTest/index.html)
* Instrumented
  tests [summary](../../app/build/reports/androidTests/connected/flavors/fakeserver/eu.benayoun.androidmoviedatabase.ui.compose.screens.home.composables.html)


